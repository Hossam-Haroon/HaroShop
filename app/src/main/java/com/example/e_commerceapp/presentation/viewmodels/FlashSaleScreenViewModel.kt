package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.domain.model.Discount
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.usecases.discountUseCases.GetAllDiscountValuesUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetAllFlashSaleProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetMostPopularProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetProductsWithSpecificDiscountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FlashSaleScreenViewModel @Inject constructor(
    getAllDiscountValuesUseCase: GetAllDiscountValuesUseCase,
    getMostPopularProductsUseCase: GetMostPopularProductsUseCase,
    private val getProductsWithSpecificDiscountUseCase: GetProductsWithSpecificDiscountUseCase,
    private val getAllFlashSaleProductsUseCase: GetAllFlashSaleProductsUseCase
) : ViewModel() {
    val discountValues : StateFlow<List<Discount>> =
        getAllDiscountValuesUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = emptyList()
            )
    val popularProducts : StateFlow<List<Product>> =
        getMostPopularProductsUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = emptyList()
            )
    private var _selectedDiscount = MutableStateFlow("10")
    val selectedDiscount = _selectedDiscount.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    @OptIn(ExperimentalCoroutinesApi::class)
    val productsWithDiscount = _selectedDiscount.filterNotNull().flatMapLatest { discountValue ->
            flow {
                _isLoading.value = true
                emit(emptyList())
                if (discountValue == "All"){
                    emitAll(getAllFlashSaleProducts())
                }else{
                    emitAll(getSpecificDiscountProducts(discountValue))
                }
            }.catch {
                _isLoading.value = false
                emit(emptyList())
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun selectDiscount(value: String) {
        _selectedDiscount.value = value
    }

    private fun getAllFlashSaleProducts(): Flow<List<Product>> {
        return flow {
            getAllFlashSaleProductsUseCase().collect{products->
                emit(products)
                _isLoading.value = false
            }
        }
    }

    private fun getSpecificDiscountProducts(discountValue:String):Flow<List<Product>>{
        return flow{
            getProductsWithSpecificDiscountUseCase(discountValue).collect{products->
                emit(products)
                _isLoading.value = false
            }
        }
    }
}