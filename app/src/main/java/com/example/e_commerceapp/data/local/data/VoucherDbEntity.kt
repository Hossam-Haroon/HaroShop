package com.example.e_commerceapp.data.local.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.e_commerceapp.domain.model.DiscountType
import com.example.e_commerceapp.domain.model.VoucherType

@Entity(tableName = "vouchers")
data class VoucherDbEntity(
    @PrimaryKey
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
