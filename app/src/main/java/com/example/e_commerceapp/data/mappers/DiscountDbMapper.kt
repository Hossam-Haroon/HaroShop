package com.example.e_commerceapp.data.mappers

import com.example.e_commerceapp.data.local.data.DiscountDbEntity
import com.example.e_commerceapp.data.remote.data.DiscountEntity
import com.example.e_commerceapp.domain.model.Discount

fun DiscountEntity.toDbEntity():DiscountDbEntity{
    return DiscountDbEntity(discountValue)
}

fun DiscountDbEntity.toDomain():Discount{
    return Discount(discountValue)
}

fun List<DiscountEntity>.toDbEntity():List<DiscountDbEntity> = this.map { it.toDbEntity() }
fun List<DiscountDbEntity>.toDomain():List<Discount> = this.map { it.toDomain() }