package com.example.e_commerceapp.data.repositories

import com.example.e_commerceapp.data.remote.BackendApiService
import com.example.e_commerceapp.data.remote.data.StripeCard
import com.example.e_commerceapp.data.requestAndResponseEntities.CreateCustomerRequest
import com.example.e_commerceapp.data.requestAndResponseEntities.ListCardsRequest
import com.example.e_commerceapp.data.requestAndResponseEntities.PaymentIntentRequest
import com.example.e_commerceapp.data.requestAndResponseEntities.PaymentIntentResponse
import com.example.e_commerceapp.data.requestAndResponseEntities.SetupIntentRequest
import com.example.e_commerceapp.domain.repositories.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val backendApi: BackendApiService
): PaymentRepository {
    override suspend fun createSetupIntent(customerId: String): Result<String> {
        return try {
            val request = SetupIntentRequest(customerId)
            val response = backendApi.createSetupIntent(request)
            Result.success(response.clientSecret)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun createCustomer(email: String): Result<String> {
        return try {
            val request = CreateCustomerRequest(email)
            val response = backendApi.createCustomer(request)
            Result.success(response.customerId)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun createPaymentIntent(
        amount: Int,
        customerId: String,
        paymentMethodId: String
    ): Result<PaymentIntentResponse> {
        return try {
            val request = PaymentIntentRequest(amount, customerId, paymentMethodId)
            val response = backendApi.createPaymentIntent(request)
            Result.success(response)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun getListCards(customerId: String):Result<List<StripeCard>> {
        return try {
            val request = ListCardsRequest(customerId)
            val response = backendApi.listCards(request)
            Result.success(response)
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}