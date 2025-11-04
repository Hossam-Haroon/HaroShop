package com.example.e_commerceapp.data.mappers

import com.example.e_commerceapp.data.remote.data.OrderEntity
import com.example.e_commerceapp.domain.model.Order


fun OrderEntity.toDomain(): Order? {
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
fun Order.toEntity(): OrderEntity {
    return OrderEntity(
        orderId,
        userId,
        items.toEntity(),
        orderNumber,
        totalPrice,
        status,
        orderDate,
        deliveryType,
        shippingAddress.toEntity()
    )
}

fun List<Order>.toEntity():List<OrderEntity> = this.map { it.toEntity() }
fun List<OrderEntity>.toDomain():List<Order> = this.map { it.toDomain()!! }



