package com.example.e_commerceapp.data.local.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carts")
data class CartDbEntity(
    @PrimaryKey
    val productId: String,
    val productAmount: Int,
    val color: Long,
    val size: String,
    val productImage: String,
    val description: String,
    val productPrice: Float,
    val category: String,
    var isSynced : Boolean = false,
    var isDeleted:Boolean = false
)
