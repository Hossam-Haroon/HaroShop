package com.example.e_commerceapp.data.repositories

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.e_commerceapp.data.local.data.ReviewDbEntity
import com.example.e_commerceapp.data.mappers.toDomain
import com.example.e_commerceapp.data.local.dataSources.LocalReviewDataSource
import com.example.e_commerceapp.data.mappers.toDbEntity
import com.example.e_commerceapp.data.remote.dataSources.RemoteImageDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteReviewDataSource
import com.example.e_commerceapp.data.workers.ReviewSyncWorker
import com.example.e_commerceapp.domain.model.Review
import com.example.e_commerceapp.domain.repositories.ReviewRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val remoteReviewDataSource: RemoteReviewDataSource,
    private val localReviewDataSource: LocalReviewDataSource,
    private val workManager: WorkManager
) : ReviewRepository {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override fun syncBackgroundDataForReviews(productId: String) {
        scope.launch {
            remoteReviewDataSource.getAllProductReviews(productId).collect { reviews ->
                //val imagesIds = reviews.map { it.userImage }.distinct()
                //val userImageMap = remoteImageDataSource.getUserImageUrls(imagesIds)
                val reviewsDbEntities = reviews.map {
                    //issue here I save the usr image url so when I get it again I use it as id
                    // to fetch the url which doesn't work right
                    //val imageUrl = userImageMap[it.userImage] ?: ""
                    it.toDbEntity(productId)
                }
                localReviewDataSource.upsertProductReviews(reviewsDbEntities)
            }
        }
    }

    override suspend fun createReview(review: Review, productId: String) {
        val newReviewId = UUID.randomUUID().toString()
        val reviewDbEntity = ReviewDbEntity(
            newReviewId,
            review.userId,
            productId,
            review.userName,
            review.userRate,
            review.reviewText,
            review.userImage,
            isSynced = false,
            toBeDeleted = false
        )
        localReviewDataSource.insertOrUpdate(reviewDbEntity)
        scheduleReviewSync()
    }

    override suspend fun deleteReview(review: Review, productId: String) {
        val reviewDbEntity = ReviewDbEntity(
            review.reviewId,
            review.userId,
            productId,
            review.userName,
            review.userRate,
            review.reviewText,
            review.userImage,
            toBeDeleted = true
        )
        localReviewDataSource.insertOrUpdate(reviewDbEntity)
        scheduleReviewSync()
    }

    override fun getAllProductReviews(productId: String): Flow<List<Review>> {
        return localReviewDataSource.getAllProductReviews(productId).map {
            it.toDomain()
        }
    }

    override suspend fun updateReview(
        reviewId: String,
        productId: String,
        userRate: Int,
        userText: String
    ) {
        val oldReview = localReviewDataSource.getReviewById(reviewId)
        val reviewDbEntity = oldReview.copy(
            userRate = userRate,
            reviewText = userText,
            isSynced = false,
            toBeDeleted = false
        )
        localReviewDataSource.insertOrUpdate(reviewDbEntity)
        scheduleReviewSync()
    }

    override fun checkIfCurrentUserHasReviewForCurrentProduct(
        productId: String,
        userId: String
    ): Flow<Review?> {
        return localReviewDataSource.getReviewForUser(productId, userId).map {
            it?.toDomain()
        }
    }

    override fun checkIfReviewsAreEmptyOrNot(productId: String): Flow<Boolean> {
        return localReviewDataSource.doesProductHasReviews(productId)
    }

    override fun getHighestRateReviewSample(productId: String): Flow<Review?> {
        return localReviewDataSource.getHighestReviewInProduct(productId).map {
            it?.toDomain()
        }
    }

    private fun scheduleReviewSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val syncRequest = OneTimeWorkRequestBuilder<ReviewSyncWorker>()
            .setConstraints(constraints)
            .build()
        workManager.enqueueUniqueWork(
            "review_sync",
            ExistingWorkPolicy.REPLACE,
            syncRequest
        )
    }
}