package com.example.e_commerceapp.domain.model

data class WishListProduct(
    val productId : String,
    val isSynced : Boolean,
    val toBeDeleted : Boolean
)
