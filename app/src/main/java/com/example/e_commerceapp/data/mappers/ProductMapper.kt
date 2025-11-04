package com.example.e_commerceapp.data.mappers

import com.example.e_commerceapp.data.local.data.ProductDbEntity
import com.example.e_commerceapp.data.remote.data.ProductEntity
import com.example.e_commerceapp.domain.model.Product

fun ProductEntity.toDomain(): Product {
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
fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        productId,
        productAmount,
        productPrice,
        productName,
        color,
        size,
        productType,
        material,
        description,
        0L,
        origin,
        discount,
        productImage
    )
}

fun ProductEntity.toDbEntity(imageUrl:String,createdAt: Long):ProductDbEntity{
    return ProductDbEntity(
        productId, productAmount, productPrice,
        productName, color, size, productType, material,
        description, favouriteCount, origin, discount, imageUrl,createdAt
    )
}


fun List<Product>.toEntity():List<ProductEntity> = this.map { it.toEntity() }
fun List<ProductEntity>.toDomain():List<Product> = this.map { it.toDomain() }