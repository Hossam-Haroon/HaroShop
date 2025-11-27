package com.example.e_commerceapp.data.local.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.e_commerceapp.data.remote.data.OrderAddressEntity
import com.example.e_commerceapp.data.remote.data.OrderItemEntity
import com.google.firebase.Timestamp

@Entity(tableName = "orders")
data class OrderDbEntity(
    @PrimaryKey
    val orderId: String,
    val userId: String,
    val items: List<OrderItemEntity>,
    val orderNumber: String,
    val totalPrice: Double,
    val status: String,
    val orderDate: Timestamp?,
    val deliveryType: String,
    @Embedded
    val shippingAddress: OrderAddressEntity,
)
