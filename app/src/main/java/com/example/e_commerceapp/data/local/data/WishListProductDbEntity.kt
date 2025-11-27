package com.example.e_commerceapp.data.local.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishListProducts")
data class WishListProductDbEntity(
    @PrimaryKey
    val productId : String,
    val isSynced : Boolean = false,
    val toBeDeleted : Boolean = false
)
