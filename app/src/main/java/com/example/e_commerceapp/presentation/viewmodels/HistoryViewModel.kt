package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.domain.model.OrderItem
import com.example.e_commerceapp.domain.model.OrderItemWithOrderDate
import com.example.e_commerceapp.domain.model.Review
import com.example.e_commerceapp.domain.model.User
import com.example.e_commerceapp.domain.usecases.orderUseCases.GetAllOrderItemsUseCase
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    getAllOrderItemsUseCase: GetAllOrderItemsUseCase,
    private val checkIfCurrentUserHasReviewForCurrentProductUseCase:
    CheckIfCurrentUserHasReviewForCurrentProductUseCase,
    private val createReviewAndUpdateUserReviewIdsUseCase: CreateReviewAndUpdateUserReviewIdsUseCase
):ViewModel() {
    val userData : StateFlow<User?> = flow {
        emit(getUserByIdUseCase())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = null
    )
    val orderItems : StateFlow<List<OrderItemWithOrderDate>> =
        getAllOrderItemsUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = emptyList()
            )
    private var _orderProductsReviewState = MutableStateFlow<Map<String, UserReviewStateCheck?>>(
        emptyMap()
    )
    val orderProductsReviewState = _orderProductsReviewState.asStateFlow()

    fun createReviewAndUpdateUserReviewsIds(
        review: Review,
        productId: String,
        orderItems:List<OrderItem>) {
        viewModelScope.launch {
            createReviewAndUpdateUserReviewIdsUseCase(review, productId)
            checkOrderProductsReviewExistence(orderItems)
        }
    }

    private fun checkOrderProductsReviewExistence(orderItems: List<OrderItem>) {
        viewModelScope.launch {
            try {
                val orderItemsMap = orderItems.map { orderItem ->
                    async {
                        val review = checkIfCurrentUserHasReviewForCurrentProductUseCase(
                            orderItem.productId
                        ).first()
                        val doesExist = (review != null)
                        val userReviewStateCheck = UserReviewStateCheck(doesExist,review)
                        orderItem.productId to userReviewStateCheck
                    }
                }.awaitAll()
                _orderProductsReviewState.value = orderItemsMap.toMap()
            }catch (e:Exception){
                _orderProductsReviewState.value = emptyMap()
            }
        }
    }
}