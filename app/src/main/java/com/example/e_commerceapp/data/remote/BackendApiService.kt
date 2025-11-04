package com.example.e_commerceapp.data.remote

import com.example.e_commerceapp.data.remote.data.StripeCard
import com.example.e_commerceapp.data.requestAndResponseEntities.ClientSecretResponse
import com.example.e_commerceapp.data.requestAndResponseEntities.CreateCustomerRequest
import com.example.e_commerceapp.data.requestAndResponseEntities.CreateCustomerResponse
import com.example.e_commerceapp.data.requestAndResponseEntities.ListCardsRequest
import com.example.e_commerceapp.data.requestAndResponseEntities.PaymentIntentRequest
import com.example.e_commerceapp.data.requestAndResponseEntities.PaymentIntentResponse
import com.example.e_commerceapp.data.requestAndResponseEntities.SetupIntentRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface BackendApiService {

    @POST("/create-customer")
    suspend fun createCustomer(@Body request: CreateCustomerRequest): CreateCustomerResponse

    @POST("/create-setup-intent")
    suspend fun createSetupIntent(@Body request: SetupIntentRequest): ClientSecretResponse

    @POST("/list-cards")
    suspend fun listCards(@Body request: ListCardsRequest): List<StripeCard>

    @POST("/create-payment-intent")
    suspend fun createPaymentIntent(@Body request: PaymentIntentRequest): PaymentIntentResponse
}