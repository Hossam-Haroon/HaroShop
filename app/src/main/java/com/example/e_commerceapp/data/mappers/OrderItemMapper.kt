package com.example.e_commerceapp.data.mappers

import com.example.e_commerceapp.data.remote.data.OrderItemEntity
import com.example.e_commerceapp.domain.model.OrderItem

fun OrderItemEntity.toDomain(): OrderItem {
    return OrderItem(
        productId, title, imageUrl, pricePaid, quantity, size, color,category
    )
}
fun OrderItem.toEntity(): OrderItemEntity {
    return OrderItemEntity(
        productId, title, imageUrl, pricePaid, quantity, size, color,category
    )
}
fun List<OrderItem>.toEntity():List<OrderItemEntity> = this.map { it.toEntity() }
fun List<OrderItemEntity>.toDomain():List<OrderItem> = this.map { it.toDomain() }