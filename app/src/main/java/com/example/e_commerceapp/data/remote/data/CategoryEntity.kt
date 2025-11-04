package com.example.e_commerceapp.data.remote.data

data class CategoryEntity(
    val categoryId : String = "",
    val categoryName : String = "",
    val productCount : Int = 0,
    val thumbnailSampleImagesId : List<String> = emptyList()
)
