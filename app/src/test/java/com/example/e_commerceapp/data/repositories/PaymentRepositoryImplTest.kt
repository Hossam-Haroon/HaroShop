package com.example.e_commerceapp.data.repositories

import com.example.e_commerceapp.data.remote.BackendApiService
import com.example.e_commerceapp.data.remote.data.Card
import com.example.e_commerceapp.data.remote.data.StripeCard
import com.example.e_commerceapp.data.requestAndResponseEntities.ClientSecretResponse
import com.example.e_commerceapp.data.requestAndResponseEntities.CreateCustomerResponse
import com.example.e_commerceapp.data.requestAndResponseEntities.PaymentIntentResponse
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PaymentRepositoryImplTest {

    private val backendApiService : BackendApiService = mockk()
    private lateinit var paymentRepositoryImpl: PaymentRepositoryImpl

    @Before
    fun setup(){
        paymentRepositoryImpl = PaymentRepositoryImpl(backendApiService)
    }

    @Test
    fun `createSetupIntent on success returns clientSecret`() = runTest {
        val clientSecretResponse = ClientSecretResponse(CLIENT_SECRET_CODE)
        coEvery { backendApiService.createSetupIntent(any()) } returns clientSecretResponse
        val result = paymentRepositoryImpl.createSetupIntent(CUSTOMER_ID)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(CLIENT_SECRET_CODE)
        coVerify(exactly = 1){
            backendApiService.createSetupIntent(match { it.customerId == CUSTOMER_ID })
        }
    }

    @Test
    fun `createSetupIntent on failure throw an exception`() = runTest {
        val exception = RuntimeException("Network Error")
        coEvery { backendApiService.createSetupIntent(any()) } throws exception
        val result = paymentRepositoryImpl.createSetupIntent(CUSTOMER_ID)
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(RuntimeException::class.java)
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Network Error")
    }

    @Test
    fun `createCustomer on success returns customerId`() = runTest {
        val customerResponse = CreateCustomerResponse(CUSTOMER_ID)
        coEvery { backendApiService.createCustomer(any()) } returns customerResponse
        val result = paymentRepositoryImpl.createCustomer(EMAIL)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(CUSTOMER_ID)
        coVerify(exactly = 1) {backendApiService.createCustomer(match { it.email == EMAIL })  }
    }

    @Test
    fun `createCustomer on failure throws an exception`() = runTest {
        val exception = RuntimeException("Network Error")
        coEvery { backendApiService.createCustomer(any()) } throws exception
        val result = paymentRepositoryImpl.createCustomer(EMAIL)
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(RuntimeException::class.java)
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Network Error")
    }

    @Test
    fun `createPaymentIntent on success returns paymentIntentResponse`() = runTest {
        val paymentResponse = PaymentIntentResponse("successful", CLIENT_SECRET_CODE)
        coEvery { backendApiService.createPaymentIntent(any()) } returns paymentResponse
        val result = paymentRepositoryImpl.createPaymentIntent(
            500, CUSTOMER_ID,"payment_123"
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(paymentResponse)
        coVerify (exactly = 1){
            backendApiService.createPaymentIntent(match { it.customerId == CUSTOMER_ID })
        }
    }

    @Test
    fun `createPaymentIntent on failure throws an exception`() = runTest {
        val exception = RuntimeException("Network Error")
        coEvery { backendApiService.createPaymentIntent(any()) } throws exception
        val result = paymentRepositoryImpl.createPaymentIntent(
            500, CUSTOMER_ID,"payment_123"
        )
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(RuntimeException::class.java)
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Network Error")
    }

    @Test
    fun `getListCards on success returns list of stripeCards`() = runTest {
        coEvery { backendApiService.listCards(any()) } returns TEST_STRIPE_CARDS_LIST
        val result = paymentRepositoryImpl.getListCards(CUSTOMER_ID)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(TEST_STRIPE_CARDS_LIST)
        coVerify(exactly = 1){ backendApiService.listCards(match { it.customerId == CUSTOMER_ID }) }
    }

    @Test
    fun `getListCards on failure throws an exception`() = runTest {
        val exception = RuntimeException("Network Error")
        coEvery { backendApiService.listCards(any()) } throws  exception
        val result = paymentRepositoryImpl.getListCards(CUSTOMER_ID)
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(RuntimeException::class.java)
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Network Error")
    }

    companion object{
        const val CUSTOMER_ID = "customer_123"
        const val CLIENT_SECRET_CODE = "client_secret_123"
        const val EMAIL = "hosamharon@gmail.com"
        val TEST_STRIPE_CARDS_LIST = listOf(
            StripeCard(
                id = "card_visa_1",
                `object` = "card",
                card = Card(
                    brand = "Visa",
                    expMonth = 12,
                    expYear = 2025,
                    last4 = "4242"
                )
            ),
            StripeCard(
                id = "card_mc_2",
                `object` = "card",
                card = Card(
                    brand = "Mastercard",
                    expMonth = 1,
                    expYear = 2028,
                    last4 = "5555"
                )
            )
        )
    }
}