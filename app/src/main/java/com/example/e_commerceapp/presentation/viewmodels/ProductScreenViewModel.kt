package com.example.e_commerceapp.presentation.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.core.Utils.BACK4APP_PRODUCTS_CLASS
import com.example.e_commerceapp.core.Utils.BACK4APP_USER_CLASS
import com.example.e_commerceapp.domain.model.HaroShopException
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.model.Review
import com.example.e_commerceapp.domain.model.User
import com.example.e_commerceapp.domain.usecases.authUseCases.GetCurrentUserUseCase
import com.example.e_commerceapp.domain.usecases.cartUseCases.InsertProductToCartUseCase
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetUploadedImageUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetMostPopularProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetProductByIdUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.ToggleFavouriteProductsUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.CheckIfCurrentUserHasReviewForCurrentProductUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.CheckIfReviewsAreEmptyOrNotUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.CreateReviewAndUpdateUserReviewIdsUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.DeleteReviewAndUpdateUserReviewsIdUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.GetHighestRateReviewSampleUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.UpdateUserReviewUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.AddLastViewedProductIdToRecentlyViewedFieldInUserDocumentUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.AddProductIdToFavouriteProductsInUserDocumentUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserByIdUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.IsProductLikedUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.RemoveProductIdFromFavouriteProductsUseCase
import com.example.e_commerceapp.presentation.uiModels.UserReviewStateCheck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductScreenViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getUploadedImageUseCase: GetUploadedImageUseCase,
    private val checkIfCurrentUserHasReviewForCurrentProductUseCase:
    CheckIfCurrentUserHasReviewForCurrentProductUseCase,
    private val checkIfReviewsAreEmptyOrNotUseCase: CheckIfReviewsAreEmptyOrNotUseCase,
    private val getHighestRateReviewSampleUseCase: GetHighestRateReviewSampleUseCase,
    private val getMostPopularProductsUseCase: GetMostPopularProductsUseCase,
    private val isProductLikedUseCase: IsProductLikedUseCase,
    private val addProductIdToFavouriteProductsInUserDocumentUseCase:
    AddProductIdToFavouriteProductsInUserDocumentUseCase,
    private val removeProductIdFromFavouriteProductsUseCase:
    RemoveProductIdFromFavouriteProductsUseCase,
    private val addLastViewedProductIdToRecentlyViewedFieldInUserDocumentUseCase:
    AddLastViewedProductIdToRecentlyViewedFieldInUserDocumentUseCase,
    private val toggleFavouriteProductsUseCase: ToggleFavouriteProductsUseCase,
    private val insertProductToCartUseCase: InsertProductToCartUseCase,
    private val getCurrentUserByIdUseCase: GetUserByIdUseCase,
    private val createReviewAndUpdateUserReviewIdsUseCase: CreateReviewAndUpdateUserReviewIdsUseCase,
    private val deleteReviewAndUpdateUserReviewsIdUseCase: DeleteReviewAndUpdateUserReviewsIdUseCase,
    private val updateUserReviewUseCase: UpdateUserReviewUseCase
) : ViewModel() {
    private var likeJob: Job? = null
    private var popularProductsJob: Job? = null
    private var _product = MutableStateFlow<Product?>(null)
    val product = _product.asStateFlow()
    private var _imageUrl = MutableStateFlow<String?>("")
    val imageUrl = _imageUrl.asStateFlow()
    private var _userReviewState = MutableStateFlow<UserReviewStateCheck?>(null)
    val userReviewState = _userReviewState.asStateFlow()
    private var _reviewsOnCurrentProductState = MutableStateFlow(false)
    val reviewsOnCurrentProductState = _reviewsOnCurrentProductState.asStateFlow()
    private var _highestRateReview = MutableStateFlow<Review?>(null)
    val highestRateReview = _highestRateReview.asStateFlow()
    private var _popularProducts = MutableStateFlow<List<Product>>(emptyList())
    val popularProducts = _popularProducts.asStateFlow()
    private var _isProductLiked = MutableStateFlow<Boolean>(false)
    val isProductLiked = _isProductLiked.asStateFlow()
    private var _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()
    private var _uiEvent = MutableSharedFlow<String>()
    val uiEvent = _uiEvent.asSharedFlow()
    fun getProductById(productId: String) {
        viewModelScope.launch {
            val product = getProductByIdUseCase(productId)
            val imageUrl =
                product?.productImage?.let {
                    getUploadedImageUseCase(it, BACK4APP_PRODUCTS_CLASS)
                }
            _imageUrl.value = imageUrl
            _product.value = product
        }
    }

    fun checkUserReviewOnTheProductState(productId: String) {
        viewModelScope.launch {
            val review = checkIfCurrentUserHasReviewForCurrentProductUseCase(productId).second
            val reviewWithImageUrl = review?.copy(
                userImage = getUploadedImageUseCase(review.userImage, BACK4APP_USER_CLASS) ?: ""
            )
            _userReviewState.value =
                reviewWithImageUrl?.let {
                    UserReviewStateCheck(
                        checkIfCurrentUserHasReviewForCurrentProductUseCase(productId).first,
                        it,
                    )
                }
        }
    }

    fun checkIfReviewsAreEmptyOrNot(productId: String) {
        viewModelScope.launch {
            _reviewsOnCurrentProductState.value =
                checkIfReviewsAreEmptyOrNotUseCase(productId)
        }
    }

    fun getHighestRateReviewAsSample(productId: String) {
        viewModelScope.launch {
            _highestRateReview.value = getHighestRateReviewSampleUseCase(productId)
        }
    }

    fun getMostPopularProducts() {
        popularProductsJob?.cancel()
        popularProductsJob = viewModelScope.launch {
            getMostPopularProductsUseCase().collect { result ->
                result.onSuccess { products ->
                    val productsWithImagesUrl = products.map { product ->
                        async {
                            product.copy(
                                productImage = getUploadedImageUseCase(
                                    product.productImage, BACK4APP_PRODUCTS_CLASS
                                ) ?: ""
                            )
                        }
                    }.awaitAll()
                    _popularProducts.value = productsWithImagesUrl
                }.onFailure {
                    _popularProducts.value = emptyList()
                }
            }
        }
    }

    fun getIsProductLikedState(productId: String) {
        likeJob?.cancel()
        likeJob = viewModelScope.launch {
            _isProductLiked.value = false
            isProductLikedUseCase(productId).catch {
                _isProductLiked.value = false
            }.collect { liked ->
                _isProductLiked.value = liked
            }
        }
    }

    fun addProductIdToFavouriteProducts(productId: String) {
        viewModelScope.launch {
            _isProductLiked.value = true
            try {
                addProductIdToFavouriteProductsInUserDocumentUseCase(productId)
            } catch (e: Exception) {
                _isProductLiked.value = false
            }

        }
    }

    fun removeProductIdFromFavouriteProducts(productId: String) {
        viewModelScope.launch {
            _isProductLiked.value = false
            try {
                removeProductIdFromFavouriteProductsUseCase(productId)
            } catch (e: Exception) {
                _isProductLiked.value = true
            }
        }
    }

    fun addLastViewedProductToRecentlyViewed(productId: String) {
        viewModelScope.launch {
            addLastViewedProductIdToRecentlyViewedFieldInUserDocumentUseCase(productId)
        }
    }

    fun toggleLikedProductsCounts(productId: String) {
        viewModelScope.launch {
            toggleFavouriteProductsUseCase(productId)
        }
    }

    fun insertProductToCart(
        product: Product,
        amount: Int,
        size: String,
        color: Long,
        category:String
    ) {
        viewModelScope.launch {
            try {
                insertProductToCartUseCase(product, amount, color, size,category)
            } catch (e: HaroShopException.ProductAlreadyInCart) {
                _uiEvent.emit("product already exist in cart")
            }
        }
    }

    fun getCurrentUserDataForReview() {
        viewModelScope.launch {
            val user = getCurrentUserByIdUseCase()
            val userWithImageUrl = user?.copy(
                imageUrl = user.imageUrl?.let { getUploadedImageUseCase(it, BACK4APP_USER_CLASS) }
            )
            _currentUser.value = userWithImageUrl
        }
    }

    fun createReviewAndUpdateUserReviewsIds(review: Review,productId: String){
        viewModelScope.launch {
            createReviewAndUpdateUserReviewIdsUseCase(review, productId)
        }
    }

    fun deleteReviewAndUpdateUserReviewsIds(review: Review,productId: String){
        viewModelScope.launch {
            deleteReviewAndUpdateUserReviewsIdUseCase(review, productId)
        }
    }

    fun updateUserReview(reviewId:String,productId:String,userRate:Int,reviewText:String){
        viewModelScope.launch {
            updateUserReviewUseCase(reviewId,productId,userRate,reviewText)
        }
    }
}