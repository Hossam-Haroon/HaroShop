package com.example.e_commerceapp.data.remote.data

import com.google.firebase.Timestamp

data class OrderEntity(
    val orderId : String = "",
    val userId : String = "",
    val items : List<OrderItemEntity> = emptyList(),
    val orderNumber : String = "",
    val totalPrice : Double = 0.0,
    val status : String = "Processing",
    val orderDate : Timestamp? =  null,
    val deliveryType : String = "Standard",
    val shippingAddress : OrderAddressEntity = OrderAddressEntity(),
)
