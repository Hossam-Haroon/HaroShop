package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.core.Utils.BACK4APP_USER_CLASS
import com.example.e_commerceapp.core.Utils.USER_IMAGE_URL
import com.example.e_commerceapp.data.mappers.USER
import com.example.e_commerceapp.domain.model.Order
import com.example.e_commerceapp.domain.model.OrderItem
import com.example.e_commerceapp.domain.model.Review
import com.example.e_commerceapp.domain.model.User
import com.example.e_commerceapp.domain.usecases.authUseCases.GetCurrentUserUseCase
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetImageIdForFetching
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetUploadedImageUseCase
import com.example.e_commerceapp.domain.usecases.orderUseCases.GetAllOrdersForCurrentUserUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.CheckIfCurrentUserHasReviewForCurrentProductUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.CreateReviewAndUpdateUserReviewIdsUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserByIdUseCase
import com.example.e_commerceapp.presentation.uiModels.UserReviewStateCheck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToReceiveScreenViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getImageIdForFetching: GetImageIdForFetching,
    private val getAllOrdersForCurrentUserUseCase: GetAllOrdersForCurrentUserUseCase,
    private val getUploadedImageUseCase: GetUploadedImageUseCase,
    private val checkIfCurrentUserHasReviewForCurrentProductUseCase:
    CheckIfCurrentUserHasReviewForCurrentProductUseCase,
    private val createReviewAndUpdateUserReviewIdsUseCase: CreateReviewAndUpdateUserReviewIdsUseCase,
    private val getCurrentUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {
    private var _userData = MutableStateFlow<User?>(
        User(
            "", "", "",
            "", emptyList(), "", emptyList(), emptyList(), null,
            ""
        )
    )
    val userData = _userData.asStateFlow()
    private var _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl = _imageUrl.asStateFlow()
    private var _userOrders = MutableStateFlow<List<Order>>(emptyList())
    val userOrders = _userOrders.asStateFlow()
    private var _userReviewState = MutableStateFlow<UserReviewStateCheck?>(null)
    val userReviewState = _userReviewState.asStateFlow()
    private var _orderProductsReviewState = MutableStateFlow<Map<String, UserReviewStateCheck?>>(
        emptyMap()
    )
    val orderProductsReviewState = _orderProductsReviewState.asStateFlow()
    private var _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    fun checkOrderProductsReviewExistence(orderItems: List<OrderItem>) {
        viewModelScope.launch {
            val newMap = mutableMapOf<String, UserReviewStateCheck?>()
            for (item in orderItems) {
                val (doesExist, review) = checkIfCurrentUserHasReviewForCurrentProductUseCase(
                    item.productId
                )
                val reviewWithImageUrl = review?.copy(
                    userImage = getUploadedImageUseCase(review.userImage, BACK4APP_USER_CLASS) ?: ""
                )
                newMap[item.productId] = reviewWithImageUrl?.let {
                    UserReviewStateCheck(doesExist, it)
                }
            }
            _orderProductsReviewState.value = newMap
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

    fun createReviewAndUpdateUserReviewsIds(
        review: Review,
        productId: String,
        orderItems:List<OrderItem>) {
        viewModelScope.launch {
            createReviewAndUpdateUserReviewIdsUseCase(review, productId)
            checkOrderProductsReviewExistence(orderItems)
        }
    }

    fun getUserProfileById() {
        viewModelScope.launch {
            _userData.value = getUserByIdUseCase()
            val imageUrl = userData.value?.let {
                getImageIdForFetching(
                    it.userId,
                    USER,
                    USER_IMAGE_URL,
                    BACK4APP_USER_CLASS
                )
            }
            _imageUrl.value = imageUrl
        }
    }

    fun getAllOrdersForUser() {
        viewModelScope.launch {
            getAllOrdersForCurrentUserUseCase().catch {
                _userOrders.value = emptyList()
            }.collect { orders ->
                _userOrders.value = orders
            }
        }
    }
}