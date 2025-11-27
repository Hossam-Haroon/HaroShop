package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.usecases.productUseCases.GetSearchedProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageSearchViewModel @Inject constructor(
    private val getSearchedProductsUseCase: GetSearchedProductsUseCase
):ViewModel() {
    private var _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()
    fun searchByLabel(label: String) {
        viewModelScope.launch {
            getSearchedProductsUseCase(label).catch {
                _products.value = emptyList()
            }.collect{products ->
                _products.value = products
            }
        }
    }
}