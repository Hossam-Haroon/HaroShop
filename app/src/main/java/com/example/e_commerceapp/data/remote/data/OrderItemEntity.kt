package com.example.e_commerceapp.data.remote.data

data class OrderItemEntity(
    val productId: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val pricePaid: Double = 0.0,
    val quantity: Int = 1,
    val size: String = "S",
    val color: Long = 0,
    val category:String = ""
)
