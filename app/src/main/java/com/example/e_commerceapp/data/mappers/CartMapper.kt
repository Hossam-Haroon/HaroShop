package com.example.e_commerceapp.data.mappers

import com.example.e_commerceapp.data.remote.data.CartEntity
import com.example.e_commerceapp.domain.model.Cart

fun CartEntity.toDomain(): Cart {
    return Cart(
        productId, productAmount, color, size, productImage, description,productPrice,category
    )
}
fun Cart.toEntity(): CartEntity {
    return CartEntity(
        productId, productAmount, color, size, productImage, description, productPrice,category
    )
}

fun List<Cart>.toEntity():List<CartEntity> = this.map { it.toEntity() }
fun List<CartEntity>.toDomain():List<Cart> = this.map { it.toDomain() }