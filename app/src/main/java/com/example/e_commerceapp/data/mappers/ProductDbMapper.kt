package com.example.e_commerceapp.data.mappers

import com.example.e_commerceapp.data.local.data.ProductDbEntity
import com.example.e_commerceapp.data.remote.data.ProductEntity
import com.example.e_commerceapp.domain.model.Product

fun ProductDbEntity.toDomain(): Product {
    return Product(
        productId,
        productAmount,
        productPrice,
        productName,
        color,
        size,
        productType,
        material,
        description,
        favouriteCount,
        origin,
        discount,
        productImage
    )
}
fun List<ProductDbEntity>.toDomain():List<Product> = this.map { it.toDomain() }