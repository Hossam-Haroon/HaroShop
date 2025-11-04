package com.example.e_commerceapp.domain.model

import com.google.firebase.Timestamp

data class OrderItemWithOrderDate(
    val productId: String,
    val title: String,
    val imageUrl: String,
    val pricePaid: Double,
    val quantity: Int,
    val size: String,
    val color: Long,
    val category:String,
    val orderDate : Timestamp = Timestamp.now(),
    val orderNumber : String = "",
)
