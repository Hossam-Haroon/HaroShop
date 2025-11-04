package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.core.Utils.BACK4APP_PRODUCTS_CLASS
import com.example.e_commerceapp.domain.model.Cart
import com.example.e_commerceapp.domain.model.Voucher
import com.example.e_commerceapp.domain.model.VoucherType
import com.example.e_commerceapp.domain.usecases.cartUseCases.GetAllCartItemsUseCase
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetUploadedImageUseCase
import com.example.e_commerceapp.domain.usecases.vouchersUseCases.GetAllUserVouchersUseCase
import com.example.e_commerceapp.presentation.utils.Utils.EXPRESS_PAYMENT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentScreenViewModel @Inject constructor(
    private val getAllCartItemsUseCase: GetAllCartItemsUseCase,
    private val getUploadedImageUseCase: GetUploadedImageUseCase,
    private val getAllUserVouchersUseCase: GetAllUserVouchersUseCase
):ViewModel() {
    private var _cartProducts = MutableStateFlow<List<Cart>>(emptyList())
    val cartProducts = _cartProducts.asStateFlow()
    private var _currentVouchers = MutableStateFlow<List<Voucher>>(emptyList())
    val currentVouchers = _currentVouchers.asStateFlow()
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
    ){products,voucher, shippingOption,standardPrice,expressPrice ->
        var baseTotalPrice = products.sumOf { it.productPrice.toDouble()*it.productAmount }
        if (voucher?.type == VoucherType.DISCOUNT){
            baseTotalPrice *= (1- voucher.discountValue/100)
        }
        val shippingPrice = if (shippingOption == EXPRESS_PAYMENT) {
            expressPrice.toDouble()
        }
        else if(voucher?.type == VoucherType.FREE_SHIPPING){
            _standardPrice.value = 0f
            0.0
        }
        else standardPrice.toDouble()
        baseTotalPrice + shippingPrice
    }.stateIn(viewModelScope, SharingStarted.Lazily,0.0)


    fun applyShippingOption(option:String){
        _selectedShippingOption.value = option
    }

    fun applyVoucher(voucher: Voucher) {
        _selectedVoucher.value = voucher
        if (voucher.type == VoucherType.FREE_SHIPPING) {
            _standardPrice.value = 0f
        }
    }

    fun resetPriceAfterCancelingVoucher(){
        _standardPrice.value = 3f
        _expressPrice.value = 12f
        _selectedVoucher.value = null
    }

    fun getAllCartItems() {
        viewModelScope.launch {
            getAllCartItemsUseCase().catch {
                _cartProducts.value = emptyList()
            }.collect { cartProducts ->
                val cartProductsWithImagesUrl = cartProducts.map { cart ->
                    async {
                        cart.copy(
                            productImage = getUploadedImageUseCase(
                                cart.productImage, BACK4APP_PRODUCTS_CLASS
                            ) ?: ""
                        )
                    }
                }.awaitAll()
                _cartProducts.value = cartProductsWithImagesUrl
            }
        }
    }

    fun getAllUserVouchers(){
        viewModelScope.launch {
            getAllUserVouchersUseCase().catch {
                _currentVouchers.value = emptyList()
            }.collect{ vouchers->
                _currentVouchers.value = vouchers
            }
        }
    }
}