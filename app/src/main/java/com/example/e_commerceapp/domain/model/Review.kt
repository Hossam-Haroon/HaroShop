package com.example.e_commerceapp.domain.model

data class Review (
    val reviewId : String,
    val userId : String,
    val userName : String,
    val userRate : Int,
    val reviewText : String,
    val userImage : String
)