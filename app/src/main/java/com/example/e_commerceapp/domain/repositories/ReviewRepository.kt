package com.example.e_commerceapp.domain.repositories

import com.example.e_commerceapp.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    suspend fun createReview(review: Review, productId: String): String
    suspend fun deleteReview(review: Review, productId: String)
    fun getAllProductReviews(productId: String): Flow<List<Review>>
    suspend fun updateReview(
        reviewId: String,
        productId: String,
        userRate: Int,
        userText: String
    )
    suspend fun checkIfCurrentUserHasReviewForCurrentProduct(
        productId: String,
        userId: String
    ): Pair<Boolean, Review?>
    suspend fun checkIfReviewsAreEmptyOrNot(productId: String): Boolean
    suspend fun getHighestRateReviewSample(productId: String): Review?
}