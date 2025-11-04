package com.example.e_commerceapp.data.remote.data

data class ProductEntity(
     var productId : String = "",
     val productAmount : Int = 0,
     val productPrice : Int = 0,
     val productName : String = "",
     val color : List<Long> = emptyList(),
     val size : List<String> = emptyList(),
     val productType : String = "",
     val material : String = "",
     val description : String = "",
     val favouriteCount : Long = 0L,
     val origin : String = "",
     val discount : Float = 0f,
     val productImage : String = ""
)
