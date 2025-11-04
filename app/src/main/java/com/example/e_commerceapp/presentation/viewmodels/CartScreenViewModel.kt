package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.core.Utils.BACK4APP_PRODUCTS_CLASS
import com.example.e_commerceapp.domain.model.Cart
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.usecases.cartUseCases.DeleteItemFromCartUseCase
import com.example.e_commerceapp.domain.usecases.cartUseCases.GetAllCartItemsUseCase
import com.example.e_commerceapp.domain.usecases.cartUseCases.InsertProductToCartUseCase
import com.example.e_commerceapp.domain.usecases.cartUseCases.UpdateCartAmountUseCase
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetUploadedImageUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetMostPopularProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetProductByIdUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.ToggleFavouriteProductsUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetFavouriteProductsUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserAddressUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.UpdateUserAddressUseCase
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
class CartScreenViewModel @Inject constructor(
    private val getFavouriteProductsUseCase: GetFavouriteProductsUseCase,
    private val getMostPopularProductsUseCase: GetMostPopularProductsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getUploadedImageUseCase: GetUploadedImageUseCase,
    private val updateUserAddressUseCase: UpdateUserAddressUseCase,
    private val getUserAddressUseCase: GetUserAddressUseCase,
    private val getAllCartItemsUseCase: GetAllCartItemsUseCase,
    private val insertProductToCartUseCase: InsertProductToCartUseCase,
    private val deleteItemFromCartUseCase: DeleteItemFromCartUseCase,
    private val toggleFavouriteProductsUseCase: ToggleFavouriteProductsUseCase,
    private val updateCartAmountUseCase: UpdateCartAmountUseCase
) : ViewModel() {
    private var _likedProducts = MutableStateFlow<List<Product>>(emptyList())
    val likedProducts = _likedProducts.asStateFlow()
    private var _popularProducts = MutableStateFlow<List<Product>>(emptyList())
    val popularProducts = _popularProducts.asStateFlow()
    private var _cartProducts = MutableStateFlow<List<Cart>>(emptyList())
    val cartProducts = _cartProducts.asStateFlow()
    private var _userAddress = MutableStateFlow("")
    val userAddress = _userAddress.asStateFlow()

    fun fetchFavouriteProductsSamples() {
        viewModelScope.launch {
            getFavouriteProductsUseCase().collect { productsIds ->
                if (productsIds.isEmpty()) {
                    _likedProducts.value = emptyList()
                    return@collect
                }
                val favouriteProducts = coroutineScope {
                    val sampleProductsId = productsIds.takeLast(5)
                    sampleProductsId.map { id ->
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

    fun updateUserAddress(address: String) {
        viewModelScope.launch {
            updateUserAddressUseCase(address)
        }
    }

    fun getUserAddress() {
        viewModelScope.launch {
            getUserAddressUseCase().collect{address ->
                _userAddress.value = address
            }
        }
    }

    fun getAllCartItems() {
        viewModelScope.launch {
            getAllCartItemsUseCase().catch {
                _cartProducts.value = emptyList()
            }.collect { cartProducts ->
                val cartProductsWithImagesUrl = cartProducts.map { cart ->
                    async {
                        cart.copy(
                            productImage = getUploadedImageUseCase(
                                cart.productImage, BACK4APP_PRODUCTS_CLASS
                            ) ?: ""
                        )
                    }
                }.awaitAll()
                _cartProducts.value = cartProductsWithImagesUrl
            }
        }
    }

    fun insertProductToCart(product: Product, amount: Int, size: String, color: Long,category:String) {
        viewModelScope.launch {
            insertProductToCartUseCase(product, amount, color, size,category)
        }
    }

    fun deleteFavouriteProduct(productId: String) {
        viewModelScope.launch {
            toggleFavouriteProductsUseCase(productId)
        }
    }

    fun deleteItemFromCart(
        cartProduct: Cart
    ) {
        viewModelScope.launch {
            deleteItemFromCartUseCase(cartProduct)
        }
    }

    fun updateCartAmount(cartProduct: Cart,amount: Int){
        viewModelScope.launch {
            updateCartAmountUseCase(cartProduct, amount)
        }
    }
}