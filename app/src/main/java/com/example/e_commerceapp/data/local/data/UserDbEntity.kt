package com.example.e_commerceapp.data.local.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_user")
data class UserDbEntity(
    @PrimaryKey
    val userId: String,
    val userName: String,
    val email: String,
    val phoneNumber: String,
    val accountRole: String,
    val recentlyViewed: List<String>,
    val favoriteProducts: List<String>,
    val reviewsId: List<String>,
    val imageUrl: String?,
    val userAddress: String,
    val stripeCustomerId: String
)
