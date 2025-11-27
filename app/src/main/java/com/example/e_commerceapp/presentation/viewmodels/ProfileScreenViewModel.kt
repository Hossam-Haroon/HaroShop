package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.domain.model.Category
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.model.User
import com.example.e_commerceapp.domain.usecases.categoryUseCases.GetSampleCategoriesUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetSampleFlashSaleProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetMostPopularProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetNewestTenProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetProductByIdUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.JustForYouProductsUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetRecentlyViewedProductIdsFromUserCollectionUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    getRecentlyViewedProductIdsFromUserCollectionUseCase:
    GetRecentlyViewedProductIdsFromUserCollectionUseCase,
    getNewestTenProductsUseCase: GetNewestTenProductsUseCase,
    getMostPopularProductsUseCase: GetMostPopularProductsUseCase,
    getSampleCategoriesUseCase: GetSampleCategoriesUseCase,
    getFlashSaleProductsUseCase: GetSampleFlashSaleProductsUseCase,
    justForYouProductsUseCase: JustForYouProductsUseCase
) : ViewModel() {
    val userData: StateFlow<User?> = flow {
        emit(getUserByIdUseCase())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = null
    )
    @OptIn(ExperimentalCoroutinesApi::class)
    val recentlyViewedProducts: StateFlow<List<Pair<String, String?>>> =
        getRecentlyViewedProductIdsFromUserCollectionUseCase().flatMapLatest { productsIds ->
            if (productsIds.isNullOrEmpty()) {
                flowOf(emptyList())
            } else {
                flow {
                    val productPairs = coroutineScope {
                        productsIds.map { productId ->
                            async {
                                val result: Pair<String, String?>?  = try {
                                    val imageUrl = getProductByIdUseCase(
                                        productId
                                    ).first()?.productImage
                                    productId to imageUrl
                                }catch (e:Exception){
                                    null
                                }
                                result
                            }
                        }.awaitAll().filterNotNull()
                    }
                    emit(productPairs)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )
    val newProducts: StateFlow<List<Product>> = getNewestTenProductsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    val popularProducts: StateFlow<List<Product>> = getMostPopularProductsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    val categories: StateFlow<List<Category>> = getSampleCategoriesUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    val flashSaleProducts: StateFlow<List<Product>> = getFlashSaleProductsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    val justForYouProducts: StateFlow<List<Product>> = justForYouProductsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}
