package com.example.e_commerceapp.domain.model

data class Product(
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
    val productImage : String
)
