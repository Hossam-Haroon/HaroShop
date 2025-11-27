package com.example.e_commerceapp.data.local.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.common.primitives.Booleans

@Entity(tableName = "products")
data class ProductDbEntity(
    @PrimaryKey
    val productId : String,
    val productAmount : Int,
    val productPrice : Int,
    val productName : String,
    val color : List<Long>,
    val size : List<String>,
    val productType : String,
    val material : String,
    val description : String,
    val favouriteCount : Long,
    val origin : String,
    val discount : Float,
    val productImage : String,
    val createdAt: Long
)
