package com.example.e_commerceapp.data.local.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class ReviewDbEntity(
    @PrimaryKey
    val reviewId: String,
    val userId: String,
    val productId:String,
    val userName: String,
    val userRate: Int,
    val reviewText: String,
    val userImage: String,
    val isSynced : Boolean = false,
    val toBeDeleted : Boolean = false
)
