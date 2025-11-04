package com.example.e_commerceapp.domain.repositories

import com.example.e_commerceapp.data.remote.data.StripeCard
import com.example.e_commerceapp.data.requestAndResponseEntities.PaymentIntentResponse

interface PaymentRepository {
    suspend fun createSetupIntent(customerId:String):Result<String>
    suspend fun createCustomer(email:String):Result<String>
    suspend fun createPaymentIntent(
         amount: Int,
         customerId: String,
         paymentMethodId: String
    ):Result<PaymentIntentResponse>

    suspend fun getListCards(customerId: String):Result<List<StripeCard>>
}