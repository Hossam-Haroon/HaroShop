package com.example.e_commerceapp.data.remote.data

data class UserEntity(
     val userId : String = "",
     val userName : String = "",
     val email : String = "",
     val phoneNumber : String = "",
     val accountRole : String = "",
     val recentlyViewed : List<String> = emptyList(),
     val favoriteProducts : List<String> = emptyList(),
     val reviewsId : List<String> = emptyList(),
     val imageUrl: String? = null,
     val userAddress : String = "",
     val stripeCustomerId : String = ""
)
