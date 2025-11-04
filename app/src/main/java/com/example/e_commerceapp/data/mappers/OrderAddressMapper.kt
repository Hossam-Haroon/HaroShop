package com.example.e_commerceapp.data.mappers

import com.example.e_commerceapp.data.remote.data.OrderAddressEntity
import com.example.e_commerceapp.domain.model.OrderAddress

fun OrderAddressEntity.toDomain(): OrderAddress {
    return OrderAddress(name, address)
}
fun OrderAddress.toEntity(): OrderAddressEntity {
    return OrderAddressEntity(name, address)
}
fun List<OrderAddress>.toEntity():List<OrderAddressEntity> = this.map { it.toEntity() }
fun List<OrderAddressEntity>.toDomain():List<OrderAddress> = this.map { it.toDomain() }