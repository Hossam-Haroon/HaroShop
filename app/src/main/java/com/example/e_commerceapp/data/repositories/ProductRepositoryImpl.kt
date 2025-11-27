package com.example.e_commerceapp.data.repositories

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.e_commerceapp.data.local.data.WishListProductDbEntity
import com.example.e_commerceapp.data.mappers.toDomain
import com.example.e_commerceapp.data.local.dataSources.LocalProductDataSource
import com.example.e_commerceapp.data.local.dataSources.LocalWishListProductsDataSource
import com.example.e_commerceapp.data.mappers.toDbEntity
import com.example.e_commerceapp.data.remote.dataSources.RemoteImageDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteProductDataSource
import com.example.e_commerceapp.data.workers.WishListSyncWorker
import com.example.e_commerceapp.domain.model.HaroShopException
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.model.WishListProduct
import com.example.e_commerceapp.domain.repositories.ProductRepository
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val localProductDataSource: LocalProductDataSource,
    private val remoteProductDataSource: RemoteProductDataSource,
    private val remoteImageDataSource: RemoteImageDataSource,
    private val localWishListProductsDataSource: LocalWishListProductsDataSource,
    private val workManager: WorkManager
) : ProductRepository {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    init {
        startBackgroundSync()
    }

    private fun startBackgroundSync() {
        scope.launch {
            remoteProductDataSource.getAllProductsStream().collect { productEntities ->
                try {
                    val imagesId = productEntities.map { it.productImage }
                    val imagesMap = remoteImageDataSource.getImageUrls(imagesId)
                    val dbEntities = productEntities.map {
                        val back4appInfo = imagesMap[it.productImage]
                        it.toDbEntity(
                            back4appInfo?.imageUrl ?: "",
                            back4appInfo?.createdAt ?: 0
                        )
                    }
                    localProductDataSource.upsertProducts(dbEntities)
                } catch (e: Exception) {
                    Log.e("ProductRepo", "Error processing product sync", e)
                }
            }
        }
    }

    override suspend fun createProduct(product: Product) {
        try {
            remoteProductDataSource.createProduct(product)
        } catch (e: FirebaseFirestoreException) {
            HaroShopException.UnableToCreateDocumentForModel(
                "unable to create product"
            )
        }
    }

    override suspend fun updateProduct(productId: String, data: Map<String, Any>) {
        try {
            remoteProductDataSource.updateProduct(productId, data)
        } catch (e: FirebaseFirestoreException) {
            HaroShopException.UnableToUpdateDocumentWithModel(
                "unable to update product with the entered data."
            )
        }
    }

    override fun getProductById(productId: String): Flow<Product?> {
        return localProductDataSource.getProductById(productId)
            .map {
                it?.toDomain()
            }
    }

    override fun getProductByCategory(productType: String): Flow<List<Product>> {
        return localProductDataSource.getCategoryProducts(productType).map { it.toDomain() }
    }

    override fun getFlashSaleProducts(limit: Long?): Flow<List<Product>> {
        return localProductDataSource.getFlashSaleProducts(limit).map {
            it.toDomain()
        }
    }

    override fun getNewestTenProducts(): Flow<List<Product>> {
        return localProductDataSource.getNewestTenProducts().map {
            it.toDomain()
        }
    }

    override fun getMostPopularProducts(): Flow<List<Product>> {
        return localProductDataSource.getPopularProducts().map {
            it.toDomain()
        }
    }

    override fun getStoryProducts(): Flow<List<Product>> {
        return localProductDataSource.getProductsWithLimit(5).map {
            it.toDomain()
        }
    }

    override fun justForYouProducts(): Flow<List<Product>> {
        return localProductDataSource.getProductsWithLimit(6).map {
            it.toDomain()
        }
    }

    override fun getProductsWithSpecificDiscount(discountValue: String): Flow<List<Product>> {
        return localProductDataSource.getProductsWithSpecificDiscountValue(
            discountValue.toInt()
        ).map {
            it.toDomain()
        }
    }

    override fun getSearchedProducts(keyword: String): Flow<List<Product>> {
        return localProductDataSource.getSearchedProducts(keyword).map {
            it.toDomain()
        }
    }

    override fun isProductLiked(productId: String): Flow<Boolean> {
        return localWishListProductsDataSource.isProductLiked(productId).map {
            it != null
        }
    }

    override suspend fun likeProduct(productId: String) {
        val wishListProduct = WishListProductDbEntity(
            productId,
            isSynced = false,
            toBeDeleted = false
        )
        localWishListProductsDataSource.insertOrUpdateFavouriteProduct(wishListProduct)
        scheduleWishListProductSync()
    }

    override suspend fun unlikeProduct(productId: String) {
        val wishListProduct = WishListProductDbEntity(
            productId,
            toBeDeleted = true
        )
        localWishListProductsDataSource.insertOrUpdateFavouriteProduct(wishListProduct)
        scheduleWishListProductSync()
    }

    override fun getALlWishListProductsIds(): Flow<List<WishListProduct>> {
        return localWishListProductsDataSource.getAllFavouriteProductsIds().map {
            it.toDomain()
        }
    }

    private fun scheduleWishListProductSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val syncRequest = OneTimeWorkRequestBuilder<WishListSyncWorker>()
            .setConstraints(constraints)
            .build()
        workManager.enqueueUniqueWork(
            "wishlist_sync",
            ExistingWorkPolicy.REPLACE,
            syncRequest
        )
    }
}