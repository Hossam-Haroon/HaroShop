package com.example.e_commerceapp.data.remote.data

data class StripeCard(
    val card: Card,
    val id: String,
    val `object`: String
)