package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.domain.model.Cart
import com.example.e_commerceapp.domain.model.Voucher
import com.example.e_commerceapp.domain.model.VoucherType
import com.example.e_commerceapp.domain.usecases.cartUseCases.GetAllCartItemsUseCase
import com.example.e_commerceapp.domain.usecases.vouchersUseCases.GetAllUserVouchersUseCase
import com.example.e_commerceapp.presentation.utils.Utils.EXPRESS_PAYMENT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PaymentScreenViewModel @Inject constructor(
    private val getAllCartItemsUseCase: GetAllCartItemsUseCase,
    private val getAllUserVouchersUseCase: GetAllUserVouchersUseCase
) : ViewModel() {
    val cartProducts: StateFlow<List<Cart>> = getAllCartItemsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    val currentVouchers: StateFlow<List<Voucher>> = getAllUserVouchersUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    private val _selectedVoucher = MutableStateFlow<Voucher?>(null)
    val selectedVoucher = _selectedVoucher.asStateFlow()
    private var _selectedShippingOption = MutableStateFlow("Standard")
    val selectedShippingOption = _selectedShippingOption.asStateFlow()
    private var _standardPrice = MutableStateFlow(3f)
    val standardPrice = _standardPrice.asStateFlow()
    private var _expressPrice = MutableStateFlow(12f)
    val expressPrice = _expressPrice.asStateFlow()
    val totalFinalPrice = combine(
        cartProducts,
        selectedVoucher,
        selectedShippingOption,
        standardPrice,
        expressPrice
    ) { products, voucher, shippingOption, standardPrice, expressPrice ->
        var baseTotalPrice = products.sumOf { it.productPrice.toDouble() * it.productAmount }
        if (voucher?.type == VoucherType.DISCOUNT) {
            baseTotalPrice *= (1 - voucher.discountValue / 100)
        }
        val shippingPrice = if (shippingOption == EXPRESS_PAYMENT) {
            expressPrice.toDouble()
        } else if (voucher?.type == VoucherType.FREE_SHIPPING) {
            _standardPrice.value = 0f
            0.0
        } else standardPrice.toDouble()
        baseTotalPrice + shippingPrice
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0.0)


    fun applyShippingOption(option: String) {
        _selectedShippingOption.value = option
    }

    fun applyVoucher(voucher: Voucher) {
        _selectedVoucher.value = voucher
        if (voucher.type == VoucherType.FREE_SHIPPING) {
            _standardPrice.value = 0f
        }
    }

    fun resetPriceAfterCancelingVoucher() {
        _standardPrice.value = 3f
        _expressPrice.value = 12f
        _selectedVoucher.value = null
    }
}