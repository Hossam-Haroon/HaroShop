package com.example.e_commerceapp.data.remote.data

data class CartEntity(
     val productId : String = "",
     val productAmount : Int = 0,
     val color : Long = 0,
     val size : String = "",
     val productImage : String = "",
     val description : String = "",
     val productPrice : Float = 0f,
     val category : String = ""
)
