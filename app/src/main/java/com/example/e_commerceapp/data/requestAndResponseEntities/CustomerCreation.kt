package com.example.e_commerceapp.data.requestAndResponseEntities

data class CreateCustomerRequest(
    val email: String
)

data class CreateCustomerResponse(
    val customerId: String
)