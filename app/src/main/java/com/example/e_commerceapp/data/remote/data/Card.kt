package com.example.e_commerceapp.data.remote.data

import com.google.gson.annotations.SerializedName

data class Card(
    val brand: String,
    @SerializedName("exp_month")
    val expMonth: Int,
    @SerializedName("exp_year")
    val expYear: Int,
    val last4: String
)