package com.example.e_commerceapp.data.remote.data

data class ReviewEntity(
     val reviewId : String? = null,
     val userId : String = "",
     val userName : String = "",
     val userRate : Int = 0,
     val reviewText : String = "",
     val userImage : String = ""
)
