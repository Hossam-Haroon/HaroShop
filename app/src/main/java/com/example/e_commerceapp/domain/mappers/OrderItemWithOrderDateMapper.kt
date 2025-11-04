package com.example.e_commerceapp.domain.mappers

import com.example.e_commerceapp.domain.model.OrderItem
import com.example.e_commerceapp.domain.model.OrderItemWithOrderDate

fun OrderItemWithOrderDate.toOrderItem(): OrderItem {
    return OrderItem(
        productId, title, imageUrl, pricePaid, quantity, size, color, category
    )
}

fun List<OrderItemWithOrderDate>.toOrderItem():List<OrderItem> = this.map { it.toOrderItem() }
