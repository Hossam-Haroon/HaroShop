package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.core.Utils.BACK4APP_PRODUCTS_CLASS
import com.example.e_commerceapp.domain.model.Discount
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.usecases.discountUseCases.GetAllDiscountValuesUseCase
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetUploadedImageUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetAllFlashSaleProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetMostPopularProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetProductsWithSpecificDiscountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlashSaleScreenViewModel @Inject constructor(
    private val getAllDiscountValuesUseCase: GetAllDiscountValuesUseCase,
    private val getMostPopularProductsUseCase: GetMostPopularProductsUseCase,
    private val getUploadedImageUseCase: GetUploadedImageUseCase,
    private val getProductsWithSpecificDiscountUseCase: GetProductsWithSpecificDiscountUseCase,
    private val getAllFlashSaleProductsUseCase: GetAllFlashSaleProductsUseCase
) : ViewModel() {
    private var _discountValues = MutableStateFlow<List<Discount>>(emptyList())
    val discountValues = _discountValues.asStateFlow()
    private var _popularProducts = MutableStateFlow<List<Product>>(emptyList())
    val popularProducts = _popularProducts.asStateFlow()
    private val _selectedDiscount = MutableStateFlow<String?>(null)
    val selectedDiscount = _selectedDiscount.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val imageCache = mutableMapOf<String, String>()
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

    fun getAllDiscountValues() {
        viewModelScope.launch {
            getAllDiscountValuesUseCase().catch {
                _discountValues.value = emptyList()
            }.collect { values ->
                _discountValues.value = values
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

    fun selectDiscount(value: String) {
        _selectedDiscount.value = value
    }

    private fun getAllFlashSaleProducts(): Flow<List<Product>> {
        return flow {
            getAllFlashSaleProductsUseCase().collect{products->
                val editedProducts = coroutineScope {
                    products.map { product ->
                        async {
                            val cachedImage = checkImageUrlInCacheAndFetchFromApiIfNot(product)
                            product.copy(productImage = cachedImage)
                        }
                    }.awaitAll()
                }
                emit(editedProducts)
                _isLoading.value = false
            }
        }
    }

    private fun getSpecificDiscountProducts(discountValue:String):Flow<List<Product>>{
        return flow{
            getProductsWithSpecificDiscountUseCase(discountValue).collect{products->
                val editedProducts = coroutineScope {
                    products.map { product ->
                        async {
                            val cachedImage = checkImageUrlInCacheAndFetchFromApiIfNot(product)
                            product.copy(productImage = cachedImage)
                        }
                    }.awaitAll()
                }
                emit(editedProducts)
                _isLoading.value = false
            }
        }
    }

    private suspend fun checkImageUrlInCacheAndFetchFromApiIfNot(product: Product):String{
        val cachedImage = imageCache[product.productImage] ?:
        getUploadedImageUseCase(
            product.productImage,
            BACK4APP_PRODUCTS_CLASS
        )?.also {
            imageCache[product.productImage] = it
        } ?: ""
        return  cachedImage
    }
}