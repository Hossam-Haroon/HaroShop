package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.core.Utils.BACK4APP_USER_CLASS
import com.example.e_commerceapp.domain.model.Review
import com.example.e_commerceapp.domain.model.User
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetUploadedImageUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.GetAllProductReviewsUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsScreenViewModel @Inject constructor(
    private val getAllProductReviewsUseCase: GetAllProductReviewsUseCase,
    private val getUploadedImageUseCase: GetUploadedImageUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
):ViewModel(){
    private var _productReviews = MutableStateFlow<List<Review>>(emptyList())
    val productReviews = _productReviews.asStateFlow()
    private var _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    fun getAllProductReviews(productId:String){
        viewModelScope.launch {
            getAllProductReviewsUseCase(productId).catch {
                _productReviews.value = emptyList()
            }.collect{reviews ->
                val reviewsWithImageUrls = reviews.map { review ->
                    async {
                        review.copy(
                            userImage = getUploadedImageUseCase(
                                review.userImage,BACK4APP_USER_CLASS
                            ) ?: ""
                        )
                    }
                }.awaitAll()
                _productReviews.value = reviewsWithImageUrls
            }
        }
    }

    fun getCurrentUser(){
        viewModelScope.launch {
            _currentUser.value = getUserByIdUseCase()
        }
    }
}