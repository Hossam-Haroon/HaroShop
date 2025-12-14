package com.example.e_commerceapp.data.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.e_commerceapp.core.Utils.PRODUCT
import com.example.e_commerceapp.core.Utils.REVIEW
import com.example.e_commerceapp.core.Utils.USER
import com.example.e_commerceapp.core.Utils.USER_REVIEWS_ID
import com.example.e_commerceapp.data.local.dao.ReviewDao
import com.example.e_commerceapp.data.mappers.toNetworkEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.tasks.await

@HiltWorker
class ReviewSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val firestore: FirebaseFirestore,
    private val reviewDao: ReviewDao,
    private val auth: FirebaseAuth
): CoroutineWorker(context,workerParameters) {
    override suspend fun doWork(): Result {
        val userId = auth.currentUser?.uid
        if (userId.isNullOrEmpty()){
            return Result.failure()
        }
        try {
            val userRef = firestore.collection(USER).document(userId)
            reviewDao.getReviewToBeDeleted()?.let {
                firestore.collection(PRODUCT).document(it.productId)
                    .collection(REVIEW).document(it.reviewId).delete().await()
                userRef.update(USER_REVIEWS_ID,FieldValue.arrayRemove(
                    it.reviewId
                )).await()
                reviewDao.deleteUserReview(it)
            }

            reviewDao.getUnSyncedReview()?.let {
                firestore.collection(PRODUCT).document(it.productId)
                    .collection(REVIEW).document(it.reviewId).set(
                        it.toNetworkEntity()
                    ).await()
                userRef.update(USER_REVIEWS_ID,FieldValue.arrayUnion(
                    it.reviewId
                )).await()
                reviewDao.markAsSynced(it.reviewId)
            }

            return Result.success()
        }catch (e:Exception){
            return Result.retry()
        }
    }
}