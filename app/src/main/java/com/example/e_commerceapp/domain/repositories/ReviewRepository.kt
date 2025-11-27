package com.example.e_commerceapp.domain.repositories

import com.example.e_commerceapp.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    suspend fun createReview(review: Review, productId: String)
    suspend fun deleteReview(review: Review, productId: String)
    fun getAllProductReviews(productId: String): Flow<List<Review>>
    suspend fun updateReview(
        reviewId: String,
        productId: String,
        userRate: Int,
        userText: String
    )
    fun checkIfCurrentUserHasReviewForCurrentProduct(
        productId: String,
        userId: String
    ):  Flow<Review?>
    fun checkIfReviewsAreEmptyOrNot(productId: String): Flow<Boolean>
    fun getHighestRateReviewSample(productId: String): Flow<Review?>
    fun syncBackgroundDataForReviews(productId: String)
}