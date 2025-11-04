package com.example.e_commerceapp.domain.model

data class User(
     val userId : String,
     val email : String,
     val userName : String,
     val phoneNumber : String,
     val favoriteProducts : List<String>,
     val accountRole : String,
     val recentlyViewed : List<String>,
     val reviewsId : List<String>,
     val imageUrl: String? = null,
     val userAddress : String,
     val stripeCustomerId : String = ""
)
