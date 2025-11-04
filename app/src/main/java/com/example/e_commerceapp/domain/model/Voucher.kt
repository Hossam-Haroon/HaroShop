package com.example.e_commerceapp.domain.model

data class Voucher(
    val code: String,
    val type: VoucherType,
    val title: String,
    val discountType: DiscountType,
    val discountValue: Double,
    val description: String,
    val freeProductId: String?,
    val expiryDate: String,
    val minOrderValue: Double,
    val isActive: Boolean
)
