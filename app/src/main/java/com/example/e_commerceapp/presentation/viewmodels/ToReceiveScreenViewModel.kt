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
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToReceiveScreenViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getAllOrdersForCurrentUserUseCase: GetAllOrdersForCurrentUserUseCase,
    private val checkIfCurrentUserHasReviewForCurrentProductUseCase:
    CheckIfCurrentUserHasReviewForCurrentProductUseCase,
    private val createReviewAndUpdateUserReviewIdsUseCase: CreateReviewAndUpdateUserReviewIdsUseCase
) : ViewModel() {
    val userData : StateFlow<User?> = getUserByIdUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = null
    )
    val userOrders : StateFlow<List<Order>> = getAllOrdersForCurrentUserUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    private var _orderProductsReviewState = MutableStateFlow<Map<String, UserReviewStateCheck?>>(
        emptyMap()
    )
    val orderProductsReviewState = _orderProductsReviewState.asStateFlow()

    fun checkOrderProductsReviewExistence(orderItems: List<OrderItem>) {
        viewModelScope.launch {
            try {
                val orderProductsMap = orderItems.map { orderItem ->
                    async {
                        val review = checkIfCurrentUserHasReviewForCurrentProductUseCase(
                            orderItem.productId
                        ).first()
                        val doesExist = (review != null)
                        val state = UserReviewStateCheck(doesExist, review)
                        orderItem.productId to state
                    }
                }.awaitAll()
                _orderProductsReviewState.value = orderProductsMap.toMap()
            } catch (e: Exception) {
                _orderProductsReviewState.value = emptyMap()
            }
        }
    }

    fun createReviewAndUpdateUserReviewsIds(
        review: Review,
        productId: String,
        orderItems: List<OrderItem>
    ) {
        viewModelScope.launch {
            createReviewAndUpdateUserReviewIdsUseCase(review, productId)
            checkOrderProductsReviewExistence(orderItems)
        }
    }
}