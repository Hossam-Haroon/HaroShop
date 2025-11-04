package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.core.Utils.BACK4APP_PRODUCTS_CLASS
import com.example.e_commerceapp.core.Utils.PRODUCT
import com.example.e_commerceapp.core.Utils.PRODUCT_IMAGE_URL
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetImageIdForFetching
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetUploadedImageUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetMostPopularProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetProductByIdUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.ToggleFavouriteProductsUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.DeleteFavouriteProductUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetFavouriteProductsUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetRecentlyViewedProductIdsFromUserCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishListScreenViewModel @Inject constructor(
    private val getRecentlyViewedProductIdsFromUserCollectionUseCase:
    GetRecentlyViewedProductIdsFromUserCollectionUseCase,
    private val getImageIdForFetching: GetImageIdForFetching,
    private val getMostPopularProductsUseCase: GetMostPopularProductsUseCase,
    private val getUploadedImageUseCase: GetUploadedImageUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getFavouriteProductsUseCase: GetFavouriteProductsUseCase,
    private val toggleFavouriteProductsUseCase: ToggleFavouriteProductsUseCase
    ) : ViewModel() {
    private var _recentlyViewedProducts = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val recentlyViewedProducts = _recentlyViewedProducts.asStateFlow()
    private var _popularProducts = MutableStateFlow<List<Product>>(emptyList())
    val popularProducts = _popularProducts.asStateFlow()
    private var _likedProducts = MutableStateFlow<List<Product>>(emptyList())
    val likedProducts = _likedProducts.asStateFlow()

    fun getRecentlyViewedProductsFromUserCollection() {
        viewModelScope.launch {
            getRecentlyViewedProductIdsFromUserCollectionUseCase()
                .catch {
                    _recentlyViewedProducts.value = emptyList()
                }.collect { productIds ->
                    if (productIds != null) {
                        mapCollectedProductsIdsToFetchUrlsFromThem(productIds)
                    }
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

    fun fetchAllFavouriteProducts() {
        viewModelScope.launch {
            getFavouriteProductsUseCase().collect { productsIds ->
                if (productsIds.isEmpty()) {
                    _likedProducts.value = emptyList()
                    return@collect
                }
                val favouriteProducts = coroutineScope {
                    productsIds.map { id ->
                        async {
                            val product = getProductByIdUseCase(id)
                            if (product != null) {
                                val imageUrl = getUploadedImageUseCase(
                                    product.productImage,
                                    BACK4APP_PRODUCTS_CLASS
                                ) ?: ""
                                product.copy(productImage = imageUrl)
                            } else null
                        }
                    }.awaitAll().filterNotNull()
                }
                _likedProducts.value = favouriteProducts
            }
        }
    }

    private suspend fun mapCollectedProductsIdsToFetchUrlsFromThem(productIds: List<String>) {
        val productPairs = coroutineScope {
            productIds.map { productId ->
                async {
                    val imageUrl = getImageIdForFetching(
                        productId, PRODUCT, PRODUCT_IMAGE_URL, BACK4APP_PRODUCTS_CLASS
                    ) ?: ""
                    productId to imageUrl
                }
            }.awaitAll()
        }
        _recentlyViewedProducts.value = productPairs
    }

    fun deleteFavouriteProduct(productId:String){
        viewModelScope.launch {
            toggleFavouriteProductsUseCase(productId)
        }
    }
}