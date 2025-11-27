package com.example.e_commerceapp.data.mappers

import com.example.e_commerceapp.data.local.data.WishListProductDbEntity
import com.example.e_commerceapp.domain.model.WishListProduct

fun WishListProductDbEntity.toDomain():WishListProduct{
    return WishListProduct(
        productId, isSynced, toBeDeleted
    )
}

fun List<WishListProductDbEntity>.toDomain():List<WishListProduct> = this.map { it.toDomain() }