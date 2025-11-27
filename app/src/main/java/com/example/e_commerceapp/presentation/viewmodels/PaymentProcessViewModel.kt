package com.example.e_commerceapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.data.remote.data.StripeCard
import com.example.e_commerceapp.data.requestAndResponseEntities.PaymentIntentResponse
import com.example.e_commerceapp.domain.model.Cart
import com.example.e_commerceapp.domain.model.Order
import com.example.e_commerceapp.domain.model.OrderAddress
import com.example.e_commerceapp.domain.model.OrderItem
import com.example.e_commerceapp.domain.usecases.cartUseCases.DeleteAllProductsInCartUseCase
import com.example.e_commerceapp.domain.usecases.orderUseCases.CreateOrderUseCase
import com.example.e_commerceapp.domain.usecases.paymentUseCases.CreatePaymentIntentUseCase
import com.example.e_commerceapp.domain.usecases.paymentUseCases.CreateSetupIntentUseCase
import com.example.e_commerceapp.domain.usecases.paymentUseCases.GetListCardsUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentProcessViewModel @Inject constructor(
    private val getListCardsUseCase: GetListCardsUseCase,
    private val createSetupIntentUseCase: CreateSetupIntentUseCase,
    private val createPaymentIntentUseCase: CreatePaymentIntentUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val deleteAllProductsInCartUseCase: DeleteAllProductsInCartUseCase,
    private val createOrderUseCase: CreateOrderUseCase
) : ViewModel() {
    private var _savedCards = MutableStateFlow<List<StripeCard>>(emptyList())
    val savedCards = _savedCards.asStateFlow()
    private val _selectedCardId = MutableStateFlow<String?>(null)
    val selectedCardId = _selectedCardId.asStateFlow()
    private val _setupIntentClientSecret = MutableStateFlow<String?>(null)
    val setupIntentClientSecret = _setupIntentClientSecret.asStateFlow()
    private val _paymentConfirmationSecret = MutableStateFlow<String?>(null)
    val paymentConfirmationSecret = _paymentConfirmationSecret.asStateFlow()
    private val _paymentResult = MutableStateFlow<String?>(null)
    val paymentResult = _paymentResult.asStateFlow()
    private var _stripeCustomerId: String? = null
    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private var _onPaymentFailed = MutableStateFlow(false)
    val onPaymentFailed = _onPaymentFailed.asStateFlow()
    private var _onPaymentSucceeded = MutableStateFlow(false)
    val onPaymentSucceeded = _onPaymentSucceeded.asStateFlow()
    init {
        loadSavedCards()
    }

    fun loadSavedCards() {
        viewModelScope.launch {
            if (_stripeCustomerId == null) {
                _stripeCustomerId = getUserByIdUseCase()?.stripeCustomerId
            }
            _stripeCustomerId?.let {
                getListCardsUseCase(it).onSuccess { cards ->
                    _savedCards.value = cards
                }.onFailure {
                    _paymentResult.value = "Error loading cards: ${it.message}"
                }
            }
        }
    }

    fun onAddNewCardClicked() {
        viewModelScope.launch {
            _stripeCustomerId?.let {
                createSetupIntentUseCase(it).onSuccess { clientSecret ->
                    _setupIntentClientSecret.value = clientSecret
                }.onFailure {
                    _paymentResult.value = "Payment failed:${it.message}"
                }
            }
        }
    }

    fun onPayClicked(
        amount: Int,
        totalPrice: Double,
        shippingOption: String,
        shippingAddress: OrderAddress,
        cartProducts: List<Cart>
    ) {
        viewModelScope.launch {
            _onPaymentFailed.value = false
            _isLoading.value = true
            val selectedCardId = selectedCardId.value
            val stripeCustomerId = _stripeCustomerId
            if (selectedCardId == null || stripeCustomerId == null) {
                _paymentResult.value = "Error: please select or add a card"
                return@launch
            }
            createPaymentIntentUseCase(
                amount,
                stripeCustomerId,
                selectedCardId
            ).onSuccess { response ->
                handlePaymentResponse(
                    response,
                    totalPrice,
                    shippingOption,
                    shippingAddress,
                    cartProducts
                )
                _isLoading.value = false
                _onPaymentSucceeded.value = true
            }.onFailure {
                _paymentResult.value = "Payment failed: ${it.message}"
                _isLoading.value = false
                _onPaymentFailed.value = true
            }
        }
    }

    fun dismissOnPaymentFailedDialog(){
        _onPaymentFailed.value = false
    }

    fun dismissOnPaymentSucceededDialog(){
        _onPaymentSucceeded.value = false
    }

    fun onCardSelected(cardId: String) {
        _selectedCardId.value = cardId
    }

    private fun handlePaymentResponse(
        response: PaymentIntentResponse,
        totalPrice: Double,
        shippingOption: String,
        shippingAddress: OrderAddress,
        cartProducts: List<Cart>
    ) {
        when (response.status) {
            "succeeded" -> {
                _paymentResult.value = "Payment Successful!"
                val orderItems = cartProducts.map { cartItem ->
                    OrderItem(
                        cartItem.productId,
                        cartItem.description,
                        cartItem.productImage,
                        cartItem.productPrice.toDouble(),
                        cartItem.productAmount,
                        cartItem.size,
                        cartItem.color,
                        cartItem.category
                    )
                }
                val order = Order(
                    items = orderItems,
                    totalPrice = totalPrice,
                    shippingAddress = shippingAddress,
                    deliveryType = shippingOption
                )
                createOrderAfterPayment(order)
            }

            "requires_action" -> {
                _paymentConfirmationSecret.value = response.clientSecret
            }

            else -> {
                _paymentResult.value = "Payment failed: ${response.status}"
            }
        }
    }

    fun onPaymentFlowFinished() {
        _setupIntentClientSecret.value = null
        _paymentConfirmationSecret.value = null
    }

    private fun clearCart(){
        viewModelScope.launch {
            deleteAllProductsInCartUseCase().onFailure {
                Log.e("PaymentViewModel", "Failed to clear cart: ${it.message}")
            }
        }
    }
    private fun createOrderAfterPayment(order: Order){
        viewModelScope.launch {
            createOrderUseCase(order).onSuccess {
                clearCart()
            }
        }
    }
}