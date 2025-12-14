package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.core.Utils.BACK4APP_USER_CLASS
import com.example.e_commerceapp.core.Utils.USER_IMAGE_URL
import com.example.e_commerceapp.data.mappers.USER
import com.example.e_commerceapp.domain.model.Order
import com.example.e_commerceapp.domain.model.User
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetImageIdForFetching
import com.example.e_commerceapp.domain.usecases.orderUseCases.GetAllOrdersForCurrentUserUseCase
import com.example.e_commerceapp.domain.usecases.orderUseCases.GetCategoriesTotalUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityScreenViewModel @Inject constructor(
    getCategoriesTotalUseCase: GetCategoriesTotalUseCase,
    getUserByIdUseCase: GetUserByIdUseCase,
    private val getAllOrdersForCurrentUserUseCase: GetAllOrdersForCurrentUserUseCase
):ViewModel() {
    val categoryTotals : StateFlow<Map<String,Float>> = getCategoriesTotalUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyMap()
    )
    private var _ordersSize = MutableStateFlow(0)
    val ordersSize = _ordersSize.asStateFlow()
    private var _receivedOrdersSize = MutableStateFlow(0)
    val receivedOrdersSize = _receivedOrdersSize.asStateFlow()
    private var _toReceiveOrdersSize = MutableStateFlow(0)
    val toReceiveOrdersSize = _toReceiveOrdersSize.asStateFlow()
    val userData : StateFlow<User?> = getUserByIdUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = null
    )
    init {
        getTotalNumberForAllOrdersUserHasDone()
    }

    private fun getTotalNumberForAllOrdersUserHasDone(){
        viewModelScope.launch {
            getAllOrdersForCurrentUserUseCase().catch {
                _ordersSize.value = 0
            }.collect{orders ->
                val ordersSize = orders.size
                val receivedOrders = orders.count { it.status == "Delivered" }
                val toReceiveOrders = ordersSize - receivedOrders
                _ordersSize.value = ordersSize
                _receivedOrdersSize.value = receivedOrders
                _toReceiveOrdersSize.value = toReceiveOrders
            }
        }
    }
}