package com.example.e_commerceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.e_commerceapp.data.local.data.ReviewDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {

    @Upsert
    suspend fun upsertAllReviews(reviews:List<ReviewDbEntity>)

    @Query("SELECT * FROM reviews WHERE productId = :productId AND toBeDeleted = 0")
    fun getAllReviewsForProduct(productId:String): Flow<List<ReviewDbEntity>>

    @Query("SELECT * FROM reviews WHERE productId = :productId ORDER BY userRate DESC LIMIT 1")
    fun getHighestRateReview(productId: String):Flow<ReviewDbEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM reviews WHERE productId = :productId)")
    fun doesProductHasReviews(productId: String):Flow<Boolean>

    @Query("SELECT * FROM reviews WHERE productId = :productId AND userId = :userId")
    fun getReviewForUser(productId: String,userId:String):Flow<ReviewDbEntity?>

    @Query("SELECT * FROM reviews WHERE reviewId = :reviewId")
    suspend fun getReviewById(reviewId: String):ReviewDbEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(reviewDbEntity: ReviewDbEntity)

    @Query("SELECT * FROM reviews WHERE isSynced = 0 AND toBeDeleted = 0")
    suspend fun getUnSyncedReview():ReviewDbEntity?

    @Query("SELECT * FROM reviews WHERE toBeDeleted = 1")
    suspend fun getReviewToBeDeleted():ReviewDbEntity?

    @Query("UPDATE reviews SET isSynced = 1 WHERE reviewId = :reviewId")
    suspend fun markAsSynced(reviewId: String)

    @Delete
    suspend fun deleteUserReview(reviewDbEntity: ReviewDbEntity)
}