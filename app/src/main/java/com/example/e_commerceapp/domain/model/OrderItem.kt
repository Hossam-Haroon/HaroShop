package com.example.e_commerceapp.domain.model

data class OrderItem(
    val productId: String,
    val title: String,
    val imageUrl: String,
    val pricePaid: Double,
    val quantity: Int,
    val size: String,
    val color: Long,
    val category:String
)
