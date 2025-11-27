package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.usecases.productUseCases.GetAllFavouriteProductsIdsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetMostPopularProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetProductByIdUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.UnLikeProductUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetRecentlyViewedProductIdsFromUserCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishListScreenViewModel @Inject constructor(
    getRecentlyViewedProductIdsFromUserCollectionUseCase:
    GetRecentlyViewedProductIdsFromUserCollectionUseCase,
    getMostPopularProductsUseCase: GetMostPopularProductsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    getAllFavouriteProductsIdsUseCase: GetAllFavouriteProductsIdsUseCase,
    private val unLikeProductUseCase: UnLikeProductUseCase
) : ViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    val recentlyViewedProducts : StateFlow<List<Pair<String,String?>>> =
        getRecentlyViewedProductIdsFromUserCollectionUseCase().flatMapLatest { productIds ->
            if (productIds.isNullOrEmpty()){
                flowOf(emptyList())
            }else{
                flow {
                    val productPairs = coroutineScope {
                        productIds.map { productId ->
                            async {
                                val imageUrl = getProductByIdUseCase(
                                    productId
                                ).first()?.productImage
                                productId to imageUrl
                            }
                        }.awaitAll()
                    }
                    emit(productPairs)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )
    val popularProducts : StateFlow<List<Product>> =
        getMostPopularProductsUseCase().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )
    @OptIn(ExperimentalCoroutinesApi::class)
    val likedProducts : StateFlow<List<Product>> = getAllFavouriteProductsIdsUseCase()
        .flatMapLatest {wishListProducts ->
            if (wishListProducts.isEmpty()){
                flowOf(emptyList())
            }else{
                flow {
                    val favouriteProductsIds = wishListProducts.map { it.productId }
                    val favouriteProducts = coroutineScope {
                        favouriteProductsIds.map {id ->
                            async {
                                getProductByIdUseCase(id).first()
                            }
                        }.awaitAll().filterNotNull()
                    }
                    emit(favouriteProducts)
                }
            }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun unlikeProduct(productId: String) {
        viewModelScope.launch {
            unLikeProductUseCase(productId)
        }
    }
}