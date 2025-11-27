package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.domain.model.Cart
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.usecases.cartUseCases.DeleteItemFromCartUseCase
import com.example.e_commerceapp.domain.usecases.cartUseCases.GetAllCartItemsUseCase
import com.example.e_commerceapp.domain.usecases.cartUseCases.UpdateCartAmountUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetAllFavouriteProductsIdsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetMostPopularProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetProductByIdUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.UnLikeProductUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserAddressUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.UpdateUserAddressUseCase
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    getAllFavouriteProductsIdsUseCase: GetAllFavouriteProductsIdsUseCase,
    getMostPopularProductsUseCase: GetMostPopularProductsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val updateUserAddressUseCase: UpdateUserAddressUseCase,
    getUserAddressUseCase: GetUserAddressUseCase,
    getAllCartItemsUseCase: GetAllCartItemsUseCase,
    private val deleteItemFromCartUseCase: DeleteItemFromCartUseCase,
    private val unLikeProductUseCase: UnLikeProductUseCase,
    private val updateCartAmountUseCase: UpdateCartAmountUseCase
) : ViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    val likedProducts : StateFlow<List<Product?>> =
        getAllFavouriteProductsIdsUseCase().flatMapLatest { wishListProduct ->
            if (wishListProduct.isEmpty()){
                flowOf(emptyList())
            }else{
                flow {
                    val sampleProductsId = wishListProduct.map { it.productId }.takeLast(5)
                    val products = coroutineScope {
                        sampleProductsId.map { id ->
                            async {
                                getProductByIdUseCase(id).first()
                            }
                        }.awaitAll()
                    }
                    emit(products)
                }
            }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    val popularProducts : StateFlow<List<Product>> = getMostPopularProductsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    val cartProducts : StateFlow<List<Cart>> = getAllCartItemsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    val totalPrice : StateFlow<Float> = cartProducts.map { carts ->
        carts.sumOf { it.productPrice.toDouble()}.toFloat()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = 0f
    )
    val userAddress: StateFlow<String> = getUserAddressUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ""
    )

    fun updateUserAddress(address: String) {
        viewModelScope.launch {
            updateUserAddressUseCase(address)
        }
    }

    fun deleteFavouriteProduct(productId: String) {
        viewModelScope.launch {
            unLikeProductUseCase(productId)
        }
    }

    fun deleteItemFromCart(
        cartProduct: Cart
    ) {
        viewModelScope.launch {
            deleteItemFromCartUseCase(cartProduct)
        }
    }

    fun updateCartAmount(cartProduct: Cart, amount: Int) {
        viewModelScope.launch {
            updateCartAmountUseCase(cartProduct, amount)
        }
    }
}