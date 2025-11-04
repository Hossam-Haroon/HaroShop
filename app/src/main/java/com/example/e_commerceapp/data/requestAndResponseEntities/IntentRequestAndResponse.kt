package com.example.e_commerceapp.data.requestAndResponseEntities

data class SetupIntentRequest(
    val customerId: String
)

data class ClientSecretResponse(
    val clientSecret: String
)