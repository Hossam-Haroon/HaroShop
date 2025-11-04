package com.example.e_commerceapp.presentation.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.core.Utils.BACK4APP_PRODUCTS_CLASS
import com.example.e_commerceapp.domain.model.Category
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.repositories.ProductRepository
import com.example.e_commerceapp.domain.usecases.categoryUseCases.GetSampleCategoriesUseCase
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetUploadedImageUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetSampleFlashSaleProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetMostPopularProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetNewestTenProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetProductDataUsingImageIdUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.JustForYouProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopScreenViewModel @Inject constructor(
    private val getSampleCategoriesUseCase: GetSampleCategoriesUseCase,
    private val getUploadedImageUseCase: GetUploadedImageUseCase,
    private val getNewestTenProductsUseCase:GetNewestTenProductsUseCase,
    private val getProductDataUsingImageIdUseCase:GetProductDataUsingImageIdUseCase,
    private val getFlashSaleProductsUseCase:GetSampleFlashSaleProductsUseCase,
    private val getMostPopularProductsUseCase:GetMostPopularProductsUseCase,
    private val justForYouProductsUseCase: JustForYouProductsUseCase,
    private val productRepository: ProductRepository

): ViewModel() {
    private var _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()
    private var _sampleImagesUrl = MutableStateFlow<Map<String, List<String>>>(emptyMap())
    val sampleImagesUrl = _sampleImagesUrl.asStateFlow()
    private var _newProducts = MutableStateFlow<List<Pair<Product?, String>>>(emptyList())
    val newProducts = _newProducts.asStateFlow()
    private var _flashSaleProducts = MutableStateFlow<List<Product>>(emptyList())
    val flashSaleProducts = _flashSaleProducts.asStateFlow()
    private var _popularProducts = MutableStateFlow<List<Product>>(emptyList())
    val popularProducts = _popularProducts.asStateFlow()
    private var _justForYouProducts = MutableStateFlow<List<Product>>(emptyList())
    val justForYouProducts = _justForYouProducts.asStateFlow()

    fun getSampleCategories() {
        viewModelScope.launch {
            getSampleCategoriesUseCase().catch {
                _categories.value = emptyList()
            }.collect { categories ->
                _categories.value = categories
                categories.forEach { category ->
                    launch {
                        val imagesUrl = category.thumbnailSampleImagesId.map {
                            async {
                                getUploadedImageUseCase(
                                    it, BACK4APP_PRODUCTS_CLASS
                                ) ?: ""
                            }
                        }.awaitAll()
                        _sampleImagesUrl.update { current ->
                            current + (category.categoryId to imagesUrl)
                        }
                    }
                }
            }
        }
    }
    fun getNewestTenProducts() {
        viewModelScope.launch {
            getNewestTenProductsUseCase().collect { result ->
                result.onSuccess { imagesId ->
                    val products = coroutineScope {
                        imagesId.map { imageId ->
                            async {
                                val product = getProductDataUsingImageIdUseCase(imageId.first)
                                product.getOrNull()?.let {
                                    Pair(it, imageId.second)
                                }
                            }
                        }
                    }.awaitAll().filterNotNull()
                    _newProducts.value = products
                }.onFailure {
                    _newProducts.value = emptyList()
                }
            }
        }
    }
    fun fetchFlashSaleProducts() {
        viewModelScope.launch {
            getFlashSaleProductsUseCase().collect { products ->
                val fetchedProductsWithImagesUrl = products.map { product ->
                    async {
                        val imageUrl = getUploadedImageUseCase(
                            product.productImage, BACK4APP_PRODUCTS_CLASS
                        ) ?: ""
                        product.copy(productImage = imageUrl)
                    }
                }.awaitAll()
                _flashSaleProducts.value = fetchedProductsWithImagesUrl
            }
        }
    }
    fun getMostPopularProducts() {
        viewModelScope.launch {
            getMostPopularProductsUseCase().collect { result ->
                result.onSuccess { products ->
                    val productsWithImageUrls = products.map { product ->
                        async {
                            product.copy(
                                productImage = getUploadedImageUseCase(
                                    product.productImage, BACK4APP_PRODUCTS_CLASS
                                ) ?: ""
                            )
                        }
                    }.awaitAll()
                    _popularProducts.value = productsWithImageUrls
                }.onFailure {
                    _popularProducts.value = emptyList()
                }
            }
        }
    }
    fun getJustForYouProducts(){
        viewModelScope.launch {
            justForYouProductsUseCase().catch {
                _justForYouProducts.value = emptyList()
            }.collect{products ->
                val productsWithImageUrls = products.map { product ->
                    async {
                        product.copy(
                            productImage = getUploadedImageUseCase(
                                product.productImage, BACK4APP_PRODUCTS_CLASS
                            ) ?: ""
                        )
                    }
                }.awaitAll()
                _justForYouProducts.value = productsWithImageUrls
            }
        }
    }
}