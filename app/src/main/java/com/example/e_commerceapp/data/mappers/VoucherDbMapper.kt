package com.example.e_commerceapp.data.mappers

import com.example.e_commerceapp.data.local.data.VoucherDbEntity
import com.example.e_commerceapp.data.remote.data.VoucherEntity
import com.example.e_commerceapp.domain.model.Voucher

fun VoucherDbEntity.toDomain(): Voucher {
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

fun VoucherEntity.toDbEntity(): VoucherDbEntity {
    return VoucherDbEntity(
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

fun List<VoucherDbEntity>.toDomain(): List<Voucher> = this.map { it.toDomain() }
fun List<VoucherEntity>.toDbEntity(): List<VoucherDbEntity> = this.map { it.toDbEntity() }