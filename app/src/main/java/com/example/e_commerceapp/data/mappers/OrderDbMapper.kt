package com.example.e_commerceapp.data.mappers

import com.example.e_commerceapp.data.local.data.CategoryDbEntity
import com.example.e_commerceapp.data.local.data.OrderDbEntity
import com.example.e_commerceapp.data.remote.data.OrderEntity
import com.example.e_commerceapp.domain.model.Category
import com.example.e_commerceapp.domain.model.Order

fun OrderEntity.toDbEntity(): OrderDbEntity? {
    return orderDate?.let {
        OrderDbEntity(
            orderId,
            userId,
            items,
            orderNumber,
            totalPrice,
            status,
            it,
            deliveryType,
            shippingAddress
        )
    }
}
fun OrderDbEntity.toDomain(): Order? {
    return orderDate?.let {
        Order(
        orderId,
        userId,
        items.toDomain(),
        totalPrice,
        orderNumber,
        status,
            it,
        deliveryType,
        shippingAddress.toDomain()
    )
    }
}

fun List<OrderEntity>.toDbEntity():List<OrderDbEntity?> = this.map { it.toDbEntity() }
fun List<OrderDbEntity>.toDomain():List<Order> = this.map { it.toDomain()!! }