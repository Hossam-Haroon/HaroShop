package com.example.e_commerceapp.data.mappers

import com.example.e_commerceapp.data.remote.data.VoucherEntity
import com.example.e_commerceapp.domain.model.Voucher

fun VoucherEntity.toDomain(): Voucher {
    return Voucher(
        code,
        type,
        title,
        discountType,
        discountValue,
        description,
        freeProductId,
        expiryDate,
        minOrderValue,
        isActive
    )
}

fun Voucher.toEntity(): VoucherEntity {
    return VoucherEntity(
        code,
        type,
        title,
        discountType,
        discountValue,
        description,
        freeProductId,
        expiryDate,
        minOrderValue,
        isActive
    )
}

fun List<Voucher>.toEntity(): List<VoucherEntity> = this.map { it.toEntity() }
fun List<VoucherEntity>.toDomain(): List<Voucher> = this.map { it.toDomain() }