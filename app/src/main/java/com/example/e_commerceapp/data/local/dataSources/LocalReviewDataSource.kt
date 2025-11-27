package com.example.e_commerceapp.data.local.dataSources

import com.example.e_commerceapp.data.local.dao.ReviewDao
import com.example.e_commerceapp.data.local.data.ReviewDbEntity
import javax.inject.Inject

class LocalReviewDataSource @Inject constructor(
    private val reviewDao: ReviewDao
) {

    suspend fun upsertProductReviews(reviews: List<ReviewDbEntity>) =
        reviewDao.upsertAllReviews(reviews)

    suspend fun getReviewById(reviewId:String) = reviewDao.getReviewById(reviewId)

    fun getAllProductReviews(productId: String) = reviewDao.getAllReviewsForProduct(productId)

    suspend fun insertOrUpdate(reviewDbEntity: ReviewDbEntity) =
        reviewDao.insertOrUpdate(reviewDbEntity)

    fun getHighestReviewInProduct(productId: String) = reviewDao.getHighestRateReview(productId)
    fun doesProductHasReviews(productId: String) = reviewDao.doesProductHasReviews(productId)
    fun getReviewForUser(productId: String, userId: String) =
        reviewDao.getReviewForUser(productId, userId)
}