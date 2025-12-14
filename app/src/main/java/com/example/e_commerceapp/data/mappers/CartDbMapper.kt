package com.example.e_commerceapp.data.mappers

import com.example.e_commerceapp.data.local.data.CartDbEntity
import com.example.e_commerceapp.data.remote.data.CartEntity
import com.example.e_commerceapp.domain.model.Cart

fun CartEntity.toDbEntity(): CartDbEntity {
    return CartDbEntity(
        productId, productAmount, color, size, productImage,
        description, productPrice, category, isSynced = true
    )
}

fun CartDbEntity.toDomain(): Cart {
    return Cart(
        productId, productAmount, color, size, productImage, description, productPrice, category
    )
}

fun CartDbEntity.toNetworkEntity(): CartEntity{
    return CartEntity(
        productId, productAmount, color, size, productImage, description, productPrice, category
    )
}

//fun List<CartEntity>.toDbEntity():List<CartDbEntity> = this.map { it.toDbEntity() }
fun List<CartDbEntity>.toDomain():List<Cart> = this.map { it.toDomain() }