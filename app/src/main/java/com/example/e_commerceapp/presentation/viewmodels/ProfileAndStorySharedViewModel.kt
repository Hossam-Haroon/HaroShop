package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.core.Utils.BACK4APP_PRODUCTS_CLASS
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetUploadedImageUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetStoryProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileAndStorySharedViewModel @Inject constructor(
    private val getStoryProductsUseCase: GetStoryProductsUseCase,
    private val getUploadedImageUseCase: GetUploadedImageUseCase
):ViewModel() {
    private var _storyProducts = MutableStateFlow<List<Product>>(emptyList())
    val storyProducts =_storyProducts.asStateFlow()

    fun getStoryProducts(){
        viewModelScope.launch {
            getStoryProductsUseCase().catch {
                _storyProducts.value = emptyList()
            }.collect{products ->
                val storyProductsWithImageUrls = products.map { product ->
                    async {
                        val imageUrl = getUploadedImageUseCase(
                            product.productImage, BACK4APP_PRODUCTS_CLASS
                        ) ?: ""
                        product.copy(productImage = imageUrl)
                    }
                }.awaitAll()
                _storyProducts.value = storyProductsWithImageUrls
            }
        }
    }
}