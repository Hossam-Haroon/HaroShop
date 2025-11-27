package com.example.e_commerceapp.data.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.e_commerceapp.core.Utils.CART
import com.example.e_commerceapp.core.Utils.USER
import com.example.e_commerceapp.data.local.dao.CartDao
import com.example.e_commerceapp.data.mappers.toNetworkEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.tasks.await

@HiltWorker
class CartSyncWorker @AssistedInject constructor(
    @Assisted appContext : Context,
    @Assisted workerParams : WorkerParameters,
    private val firestore: FirebaseFirestore,
    private val cartDao: CartDao,
    private val firebaseAuth: FirebaseAuth
) : CoroutineWorker(appContext,workerParams) {
    override suspend fun doWork(): Result {
        Log.d("CartSyncWorker", "doWork() STARTED.")
        val userId = firebaseAuth.currentUser?.uid
        if (userId.isNullOrEmpty()){
            Log.e("CartSyncWorker", "FATAL: User is NULL. Worker is failing.")
            return Result.failure()
        }
        Log.d("CartSyncWorker", "User ID $userId found. Starting sync.")
        val cartRef = firestore.collection(USER).document(userId).collection(CART)
        try {
            val cartsToDelete = cartDao.getDeletedItems()
            cartsToDelete.forEach { cart ->
                cartRef.document(cart.productId).delete().await()
                cartDao.deleteItemPermanently(cart)
            }
            val itemsToAdd = cartDao.getUnSyncedCarts()
            itemsToAdd.forEach { cart ->
                cartRef.document(cart.productId).set(cart.toNetworkEntity()).await()
                cartDao.markCartAsSynced(cart.productId)
            }
            return Result.success()
        }catch (e:Exception){
            return Result.retry()
        }
    }
}