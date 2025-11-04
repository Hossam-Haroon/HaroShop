package com.example.e_commerceapp.data.mappers

import com.example.e_commerceapp.data.remote.data.DiscountEntity
import com.example.e_commerceapp.domain.model.Discount

fun DiscountEntity.toDomain(): Discount {
    return Discount(
        discountValue
    )
}
fun Discount.toEntity(): DiscountEntity {
    return DiscountEntity(
        discountValue
    )
}

fun List<Discount>.toEntity():List<DiscountEntity> = this.map { it.toEntity() }
fun List<DiscountEntity>.toDomain():List<Discount> = this.map { it.toDomain() }