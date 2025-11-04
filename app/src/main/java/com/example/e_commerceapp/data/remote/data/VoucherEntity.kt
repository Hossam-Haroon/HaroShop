package com.example.e_commerceapp.data.remote.data

import com.example.e_commerceapp.domain.model.DiscountType
import com.example.e_commerceapp.domain.model.VoucherType

data class VoucherEntity(
    val code: String = "",
    val type: VoucherType = VoucherType.DISCOUNT,
    val title: String = "",
    val discountType: DiscountType = DiscountType.NONE,
    val discountValue: Double = 0.0,
    val description: String = "",
    val freeProductId: String? = null,
    val expiryDate: String = "",
    val minOrderValue: Double = 0.0,
    val isActive: Boolean = false
)
