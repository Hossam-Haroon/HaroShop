package com.example.e_commerceapp.presentation.viewmodels

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
    private val getUploadedImageUseCase: GetUploadedImageUseCase
):ViewModel() {
    private var _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()
    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    fun getAllProducts(productType : String){
        viewModelScope.launch {
            _isLoading.value = true
            getProductByCategoryUseCase(productType).catch {
                _products.value = emptyList()
            }.collect{ products ->
                val updatedProductsWithImagesUrl = products.map { product ->
                    product.copy(
                        productImage = getUploadedImageUseCase(
                            product.productImage,BACK4APP_PRODUCTS_CLASS
                        ) ?: ""
                    )
                }
                _products.value = updatedProductsWithImagesUrl
                _isLoading.value = false
            }
        }
    }
}