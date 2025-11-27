package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.domain.model.HaroShopException
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.model.Review
import com.example.e_commerceapp.domain.model.User
import com.example.e_commerceapp.domain.usecases.cartUseCases.InsertProductToCartUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetMostPopularProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetProductByIdUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.LikeProductUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.UnLikeProductUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.CheckIfCurrentUserHasReviewForCurrentProductUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.CheckIfReviewsAreEmptyOrNotUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.CreateReviewAndUpdateUserReviewIdsUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.DeleteReviewAndUpdateUserReviewsIdUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.GetHighestRateReviewSampleUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.UpdateUserReviewUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.AddLastViewedProductIdToRecentlyViewedFieldInUserDocumentUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserByIdUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.IsProductLikedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductScreenViewModel @Inject constructor(
    getProductByIdUseCase: GetProductByIdUseCase,
    checkIfCurrentUserHasReviewForCurrentProductUseCase:
    CheckIfCurrentUserHasReviewForCurrentProductUseCase,
    checkIfReviewsAreEmptyOrNotUseCase: CheckIfReviewsAreEmptyOrNotUseCase,
    getHighestRateReviewSampleUseCase: GetHighestRateReviewSampleUseCase,
    getMostPopularProductsUseCase: GetMostPopularProductsUseCase,
    private val addLastViewedProductIdToRecentlyViewedFieldInUserDocumentUseCase:
    AddLastViewedProductIdToRecentlyViewedFieldInUserDocumentUseCase,
    private val insertProductToCartUseCase: InsertProductToCartUseCase,
    private val getCurrentUserByIdUseCase: GetUserByIdUseCase,
    private val createReviewAndUpdateUserReviewIdsUseCase: CreateReviewAndUpdateUserReviewIdsUseCase,
    private val deleteReviewAndUpdateUserReviewsIdUseCase: DeleteReviewAndUpdateUserReviewsIdUseCase,
    private val updateUserReviewUseCase: UpdateUserReviewUseCase,
    private val likeProductUseCase: LikeProductUseCase,
    private val unLikeProductUseCase: UnLikeProductUseCase,
    isProductLikedUseCase: IsProductLikedUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val productId = savedStateHandle.get<String>("productId") ?: ""
    val product : StateFlow<Product?> = getProductByIdUseCase(productId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )
    val userReviewState: StateFlow<Review?> =
        checkIfCurrentUserHasReviewForCurrentProductUseCase(productId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )
    val reviewsOnCurrentProductState: StateFlow<Boolean> =
        checkIfReviewsAreEmptyOrNotUseCase(productId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )
    val highestRateReview: StateFlow<Review?> =
        getHighestRateReviewSampleUseCase(productId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )
    val popularProducts: StateFlow<List<Product>> = getMostPopularProductsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    val isProductLiked: StateFlow<Boolean> = isProductLikedUseCase(productId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = false
    )
    val currentUser: StateFlow<User?> = flow {
        emit(getCurrentUserByIdUseCase())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = null
    )
    private var _uiEvent = MutableSharedFlow<String>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        addLastViewedProductToRecentlyViewed(productId)
    }

    private fun addLastViewedProductToRecentlyViewed(productId: String) {
        viewModelScope.launch {
            addLastViewedProductIdToRecentlyViewedFieldInUserDocumentUseCase(productId)
        }
    }

    fun toggleLikedProductsCounts() {
        viewModelScope.launch {
            if (isProductLiked.value){
                unLikeProductUseCase(productId)
            }else{
                likeProductUseCase(productId)
            }
        }
    }

    fun insertProductToCart(
        product: Product,
        amount: Int,
        size: String,
        color: Long,
        category: String
    ) {
        viewModelScope.launch {
            try {
                insertProductToCartUseCase(product, amount, color, size, category)
            } catch (e: HaroShopException.ProductAlreadyInCart) {
                _uiEvent.emit("product already exist in cart")
            }
        }
    }

    fun createReviewAndUpdateUserReviewsIds(review: Review, productId: String) {
        viewModelScope.launch {
            createReviewAndUpdateUserReviewIdsUseCase(review, productId)
        }
    }

    fun deleteReviewAndUpdateUserReviewsIds(review: Review, productId: String) {
        viewModelScope.launch {
            deleteReviewAndUpdateUserReviewsIdUseCase(review, productId)
        }
    }

    fun updateUserReview(reviewId: String, productId: String, userRate: Int, reviewText: String) {
        viewModelScope.launch {
            updateUserReviewUseCase(reviewId, productId, userRate, reviewText)
        }
    }
}