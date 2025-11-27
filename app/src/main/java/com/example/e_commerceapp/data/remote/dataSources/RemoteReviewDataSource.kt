package com.example.e_commerceapp.data.remote.dataSources

import com.example.e_commerceapp.core.Utils.PRODUCT
import com.example.e_commerceapp.core.Utils.REVIEW
import com.example.e_commerceapp.core.Utils.REVIEW_TEXT
import com.example.e_commerceapp.core.Utils.USER_RATE
import com.example.e_commerceapp.data.mappers.toEntity
import com.example.e_commerceapp.data.remote.data.ReviewEntity
import com.example.e_commerceapp.domain.model.Review
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteReviewDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    fun getAllProductReviews(productId: String): Flow<List<ReviewEntity>> {
        return callbackFlow {
            val listener = firestore.collection(PRODUCT)
                .document(productId)
                .collection(REVIEW)
                .orderBy(USER_RATE, Query.Direction.DESCENDING)
                .addSnapshotListener { snapShot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val reviews = snapShot?.toObjects(ReviewEntity::class.java) ?: emptyList()
                    trySend(reviews)
                }
            awaitClose { listener.remove() }
        }
    }

    suspend fun createReview(review: Review, productId: String): String {
        val docRef = firestore.collection(PRODUCT)
            .document(productId)
            .collection(REVIEW)
            .document()
        val docId = docRef.id
        val reviewEntity = review.copy(reviewId = docId).toEntity()
        docRef.set(reviewEntity).await()
        return docId
    }

    fun deleteReview(review: Review, productId: String) {
        review.reviewId?.let {
            firestore.collection(PRODUCT)
                .document(productId)
                .collection(REVIEW)
                .document(it)
                .delete()
        }
    }

    suspend fun updateReview(
        reviewId: String,
        productId: String,
        userRate:Int,
        userText:String
    ) {
        firestore.collection(PRODUCT)
            .document(productId)
            .collection(REVIEW)
            .document(reviewId)
            .update(
                mapOf(
                    USER_RATE to userRate,
                    REVIEW_TEXT to userText
                )
            )
            .await()
    }
}