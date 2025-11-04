package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.core.Utils.BACK4APP_USER_CLASS
import com.example.e_commerceapp.core.Utils.USER_IMAGE_URL
import com.example.e_commerceapp.data.mappers.USER
import com.example.e_commerceapp.domain.mappers.toOrderItem
import com.example.e_commerceapp.domain.model.OrderItem
import com.example.e_commerceapp.domain.model.OrderItemWithOrderDate
import com.example.e_commerceapp.domain.model.Review
import com.example.e_commerceapp.domain.model.User
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetImageIdForFetching
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetUploadedImageUseCase
import com.example.e_commerceapp.domain.usecases.orderUseCases.GetAllOrderItemsUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.CheckIfCurrentUserHasReviewForCurrentProductUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.CreateReviewAndUpdateUserReviewIdsUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserByIdUseCase
import com.example.e_commerceapp.presentation.uiModels.UserReviewStateCheck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getImageIdForFetching: GetImageIdForFetching,
    private val getAllOrderItemsUseCase: GetAllOrderItemsUseCase,
    private val getUploadedImageUseCase: GetUploadedImageUseCase,
    private val checkIfCurrentUserHasReviewForCurrentProductUseCase:
    CheckIfCurrentUserHasReviewForCurrentProductUseCase,
    private val createReviewAndUpdateUserReviewIdsUseCase: CreateReviewAndUpdateUserReviewIdsUseCase
):ViewModel() {
    private var _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl = _imageUrl.asStateFlow()
    private var _userData = MutableStateFlow<User?>(
        User(
            "", "", "",
            "", emptyList(), "", emptyList(), emptyList(), null,
            ""
        )
    )
    val userData = _userData.asStateFlow()
    private var _orderItems = MutableStateFlow<List<OrderItemWithOrderDate>>(emptyList())
    val orderItems = _orderItems.asStateFlow()
    private var _orderProductsReviewState = MutableStateFlow<Map<String, UserReviewStateCheck?>>(
        emptyMap()
    )
    val orderProductsReviewState = _orderProductsReviewState.asStateFlow()
    private var _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    fun getCurrentUserDataForReview() {
        viewModelScope.launch {
            val user = getUserByIdUseCase()
            val userWithImageUrl = user?.copy(
                imageUrl = user.imageUrl?.let { getUploadedImageUseCase(it, BACK4APP_USER_CLASS) }
            )
            _currentUser.value = userWithImageUrl
            _imageUrl.value = userWithImageUrl?.imageUrl
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

    fun getAllOrderItems(){
        viewModelScope.launch {
            getAllOrderItemsUseCase().catch {
                _orderItems.value = emptyList()
            }.collect{orderItems ->
                _orderItems.value = orderItems
                checkOrderProductsReviewExistence(orderItems.toOrderItem())
            }
        }
    }

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
}