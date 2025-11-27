package com.example.e_commerceapp.data.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.e_commerceapp.core.Utils.FAVOURITE_COUNT
import com.example.e_commerceapp.core.Utils.FAVOURITE_PRODUCTS
import com.example.e_commerceapp.core.Utils.PRODUCT
import com.example.e_commerceapp.core.Utils.USER
import com.example.e_commerceapp.data.local.dao.WishListProductsDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.tasks.await

@HiltWorker
class WishListSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val firestore: FirebaseFirestore,
    private val wishListProductsDao: WishListProductsDao,
    private val auth: FirebaseAuth
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val userId = auth.currentUser?.uid
        if (userId.isNullOrEmpty()) {
            return Result.failure()
        }
        val userRef = firestore.collection(USER).document(userId)
        try {
            val requiredToBeDeletedFavouriteProducts =
                wishListProductsDao.getProductsIdsToBeDeleted()
            requiredToBeDeletedFavouriteProducts.forEach { wishlistProduct ->
                val productRef = firestore.collection(PRODUCT).document(wishlistProduct.productId)
                firestore.runBatch { batch ->
                    batch.update(
                        userRef,
                        FAVOURITE_PRODUCTS,
                        FieldValue.arrayRemove(wishlistProduct.productId)
                    )
                    batch.update(
                        productRef,
                        FAVOURITE_COUNT,
                        FieldValue.increment(-1)
                    )
                }.await()
                wishListProductsDao.deleteProductId(wishlistProduct)
            }
            val productsToBeSynced = wishListProductsDao.getUnSyncedProductsIds()
            productsToBeSynced.forEach { wishListProduct ->
                val productRef = firestore.collection(PRODUCT).document(wishListProduct.productId)
                firestore.runBatch { batch ->
                    batch.update(
                        userRef,
                        FAVOURITE_PRODUCTS,
                        FieldValue.arrayUnion(wishListProduct.productId)
                    )
                    batch.update(
                        productRef,
                        FAVOURITE_COUNT,
                        FieldValue.increment(1)
                    )
                }
                wishListProductsDao.markProductIdAsSynced(wishListProduct.productId)
            }
            return Result.success()
        } catch (e: Exception) {
            return Result.retry()
        }
    }
}