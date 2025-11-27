package com.example.e_commerceapp.data.local.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "discounts")
data class DiscountDbEntity(
    @PrimaryKey
    val discountValue : String
)
