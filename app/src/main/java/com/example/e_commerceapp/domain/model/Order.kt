package com.example.e_commerceapp.domain.model

import com.google.firebase.Timestamp

data class Order(
    val orderId : String = "",
    val userId : String = "",
    val items : List<OrderItem>,
    val totalPrice : Double,
    val orderNumber : String = "",
    val status : String = "Processing",
    val orderDate : Timestamp = Timestamp.now(),
    val deliveryType : String = "Standard",
    val shippingAddress : OrderAddress,
)
