package com.example.e_commerceapp.data.repositories


import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.e_commerceapp.data.local.data.CartDbEntity
import com.example.e_commerceapp.data.local.dataSources.LocalCartDataSource
import com.example.e_commerceapp.data.mappers.toDbEntity
import com.example.e_commerceapp.data.mappers.toDomain
import com.example.e_commerceapp.data.remote.dataSources.RemoteAuthDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteCartDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteImageDataSource
import com.example.e_commerceapp.data.workers.CartSyncWorker
import com.example.e_commerceapp.domain.model.Cart
import com.example.e_commerceapp.domain.model.HaroShopException
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.repositories.CartRepository
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val remoteCartDataSource: RemoteCartDataSource,
    private val localCartDataSource: LocalCartDataSource,
    private val remoteImageDataSource: RemoteImageDataSource,
    authDataSource: RemoteAuthDataSource,
    private val workManager: WorkManager
) : CartRepository {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        authDataSource.getCurrentUser()?.uid?.let {
            syncBackgroundData(it)
        }
    }

    private fun syncBackgroundData(userId: String) {
        scope.launch {
            remoteCartDataSource.getAllCartItems(userId).collect { carts ->
                val imagesIds = carts.map { it.productImage }
                val imagesMap = remoteImageDataSource.getImageUrls(imagesIds)
                val cartsDbEntity = carts.map {
                    val back4appInfo = imagesMap[it.productImage]
                    it.toDbEntity(back4appInfo?.imageUrl ?: "")
                }
                localCartDataSource.upsertAllCarts(cartsDbEntity)
            }
        }
    }

    override fun getAllCartItems(userId: String): Flow<List<Cart>> {
        return localCartDataSource.getAllCarts().map {
            it.toDomain()
        }
    }

    override suspend fun updateCartQuantity(userId: String, cartProduct: Cart, quantity: Int) {
        try {
            remoteCartDataSource.updateCartQuantity(userId, cartProduct, quantity)
        } catch (e: FirebaseFirestoreException) {
            throw HaroShopException.UnableToUpdateDocumentWithModel("unable to upgrade cart amount")
        }
    }

    override suspend fun deleteAllProductsInCart(userId: String): Result<Unit> {
        return try {
            remoteCartDataSource.deleteAllProductsInCart(userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteItemFromCart(
        cartProduct: Cart
    ) {
        val cartToBeDeleted = CartDbEntity(
            cartProduct.productId,
            cartProduct.productAmount,
            cartProduct.color,
            cartProduct.size,
            cartProduct.productImage,
            cartProduct.description,
            cartProduct.productPrice,
            cartProduct.category,
            isDeleted = true
        )
        localCartDataSource.insertOrUpdateCart(cartToBeDeleted)
        scheduleCartSync()
    }

    override suspend fun insertProductToCart(
        product: Product,
        productAmount: Int,
        productColor: Long,
        productSize: String,
        category: String
    ) {
        val uniqueCartId = "${product.productId}_${productColor}_$productSize"
        val unSyncedCart = CartDbEntity(
            uniqueCartId,
            productAmount,
            productColor,
            productSize,
            product.productImage,
            product.description,
            product.productPrice.toFloat(),
            product.productType,
            isSynced = false,
            isDeleted = false
        )
        localCartDataSource.insertOrUpdateCart(unSyncedCart)
        scheduleCartSync()
    }

    private fun scheduleCartSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val syncRequest = OneTimeWorkRequestBuilder<CartSyncWorker>()
            .setConstraints(constraints)
            .build()
        workManager.enqueueUniqueWork(
            "cart_sync",
            ExistingWorkPolicy.REPLACE,
            syncRequest
        )
    }
}