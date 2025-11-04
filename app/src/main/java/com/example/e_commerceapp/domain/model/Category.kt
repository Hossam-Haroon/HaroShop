package com.example.e_commerceapp.domain.model

data class Category(
    val categoryId : String,
    val categoryName : String,
    val productCount : Int,
    val thumbnailSampleImagesId : List<String>
)
