package com.example.e_commerceapp.data.remote

import com.example.e_commerceapp.data.requestAndResponseEntities.CreateCustomerRequest
import com.example.e_commerceapp.data.requestAndResponseEntities.ListCardsRequest
import com.example.e_commerceapp.data.requestAndResponseEntities.PaymentIntentRequest
import com.example.e_commerceapp.data.requestAndResponseEntities.PaymentIntentResponse
import com.example.e_commerceapp.data.requestAndResponseEntities.SetupIntentRequest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertFailsWith

class BackendApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var backendApiService: BackendApiService

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/")
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        backendApiService = retrofit.create(BackendApiService::class.java)
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

    @Test
    fun `listCards should take a request and return a list of stripeCards`() = runTest{
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(FAKE_JSON)
        )
        val requestBody = ListCardsRequest("customer_123")
        val responseList = backendApiService.listCards(requestBody)
        val recordedRequest = mockWebServer.takeRequest()

        assertThat(recordedRequest.path).isEqualTo("/list-cards")
        assertThat(recordedRequest.method).isEqualTo("POST")
        assertThat(responseList).hasSize(2)
        assertThat(responseList[0].card.last4).isEqualTo("4242")
    }

    @Test
    fun `listCards should throw exception if there is HttpError`() = runTest {
        val customerId = "invalid_customer_id"
        val requestBody = ListCardsRequest(customerId)
        mockWebServer.enqueue(
            MockResponse().setResponseCode(404).setBody("{\"error\": \"Customer not found\"}")
        )
        assertFailsWith<HttpException> {
            backendApiService.listCards(requestBody)
        }
        val recordedRequest = mockWebServer.takeRequest()
        assertThat(recordedRequest.path).isEqualTo("/list-cards")
        assertThat(recordedRequest.method).isEqualTo("POST")
    }

    @Test
    fun `createCustomer should take a request and return customerResponse`() = runTest{
        val customerRequest = CreateCustomerRequest("hosamharon2016@gmail.com")
        val customerId = "customer_123"
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(CUSTOMER_ID_JSON)
        )
        val customerResult = backendApiService.createCustomer(customerRequest)
        val recordedResponse = mockWebServer.takeRequest()
        assertThat(recordedResponse.path).isEqualTo("/create-customer")
        assertThat(recordedResponse.method).isEqualTo("POST")
        assertThat(customerResult.customerId).isEqualTo(customerId)
    }

    @Test
    fun `createCustomer should throw exception if email is invalid`() = runTest {
        val customerRequest = CreateCustomerRequest("invalid email")
        mockWebServer.enqueue(
            MockResponse().setResponseCode(400).setBody("{\"error\": \"Invalid email format\"}")
        )
        assertFailsWith<HttpException> {
            backendApiService.createCustomer(customerRequest)
        }
        val recordedResponse = mockWebServer.takeRequest()
        assertThat(recordedResponse.path).isEqualTo("/create-customer")
        assertThat(recordedResponse.method).isEqualTo("POST")
    }

    @Test
    fun `createSetupIntent should return clientResponse when we pass intentRequest`() = runTest {
        val setupIntentRequest = SetupIntentRequest("customer_123")
        val clientSecret = "A1Z2S3"
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(CLIENT_SECRET_JSON)
        )
        val clientResult = backendApiService.createSetupIntent(setupIntentRequest)
        val recordedResponse = mockWebServer.takeRequest()
        assertThat(recordedResponse.path).isEqualTo("/create-setup-intent")
        assertThat(recordedResponse.method).isEqualTo("POST")
        assertThat(clientResult.clientSecret).isEqualTo(clientSecret)
    }

    @Test
    fun `createSetupIntent should throw exception if customerId is invalid`() = runTest {
        val setupIntentRequest = SetupIntentRequest("invalid customer Id")
        mockWebServer.enqueue(
            MockResponse().setResponseCode(400).setBody("{\"error\": \"Invalid customerId\"}")
        )
        assertFailsWith<HttpException> {
            backendApiService.createSetupIntent(setupIntentRequest)
        }
        val recordedResponse = mockWebServer.takeRequest()
        assertThat(recordedResponse.path).isEqualTo("/create-setup-intent")
        assertThat(recordedResponse.method).isEqualTo("POST")
    }

    @Test
    fun `createPaymentIntent should return paymentResponse if paymentRequest is valid`() = runTest {
        val amount = 500
        val customerId = "customer_123"
        val paymentMethodId = "visa_123"
        val paymentIntentResponse = PaymentIntentResponse("ok","A1Z2S3")
        val paymentRequest = PaymentIntentRequest(amount,customerId,paymentMethodId)
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(PAYMENT_RESPONSE_JSON)
        )
        val paymentResult = backendApiService.createPaymentIntent(paymentRequest)
        val recordedResponse = mockWebServer.takeRequest()
        assertThat(recordedResponse.path).isEqualTo("/create-payment-intent")
        assertThat(recordedResponse.method).isEqualTo("POST")
        assertThat(paymentResult).isEqualTo(paymentIntentResponse)
    }

    @Test
    fun `createPaymentIntent should throw exception if paymentRequest is invalid`() = runTest {
        val amount = 500
        val customerId = "invalid customerId"
        val paymentMethodId = "visa_123"
        val paymentRequest = PaymentIntentRequest(amount,customerId,paymentMethodId)
        mockWebServer.enqueue(
            MockResponse().setResponseCode(404).setBody("{\"error\": \"Invalid paymentRequest\"}")
        )
        assertFailsWith<HttpException> {
            backendApiService.createPaymentIntent(paymentRequest)
        }
        val recordedResponse = mockWebServer.takeRequest()
        assertThat(recordedResponse.path).isEqualTo("/create-payment-intent")
        assertThat(recordedResponse.method).isEqualTo("POST")
    }


    companion object{
        val FAKE_JSON = """
            [
                {
            "id": "card_123",
            "object": "card",
            "card": {
              "brand": "Visa",
              "exp_month": 12,
              "exp_year": 2025,
              "last4": "4242"
            }
          },
          {
            "id": "card_456",
            "object": "card",
            "card": {
              "brand": "Mastercard",
              "exp_month": 10,
              "exp_year": 2024,
              "last4": "5555"
            }
          }
            ]
        """.trimIndent()
    }

    val CUSTOMER_ID_JSON = """{"customerId": "customer_123"}"""
    val CLIENT_SECRET_JSON = """{"clientSecret": "A1Z2S3"}"""
    val PAYMENT_RESPONSE_JSON = """{"status": "ok", "clientSecret": "A1Z2S3"}"""

}