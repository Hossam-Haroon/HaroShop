package com.example.e_commerceapp.data.requestAndResponseEntities

data class PaymentIntentRequest(
    val amount: Int,
    val customerId: String,
    val paymentMethodId: String
)


data class PaymentIntentResponse(
    val status: String,
    val clientSecret: String?
)