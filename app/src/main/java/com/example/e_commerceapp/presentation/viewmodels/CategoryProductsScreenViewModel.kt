package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.core.Utils.BACK4APP_PRODUCTS_CLASS
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetUploadedImageUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetProductByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryProductsScreenViewModel @Inject constructor(
    private val getProductByCategoryUseCase: GetProductByCategoryUseCase,
    private val savedStateHandle: SavedStateHandle
):ViewModel() {
    val categoryName = savedStateHandle.get<String>("categoryName") ?: ""
    private var _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()
    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    init {
        getAllProducts(categoryName)
    }
    private fun getAllProducts(productType : String){
        viewModelScope.launch {
            _isLoading.value = true
            getProductByCategoryUseCase(productType).catch {
                _products.value = emptyList()
            }.collect{ products ->
                _products.value = products
                _isLoading.value = false
            }
        }
    }
}