package com.example.e_commerceapp.domain.usecases.orderUseCases

import com.example.e_commerceapp.domain.model.Order
import com.example.e_commerceapp.domain.repositories.OrderRepository
import com.example.e_commerceapp.domain.usecases.authUseCases.GetCurrentUserUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllOrdersForCurrentUserUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) {
    operator fun invoke(): Flow<List<Order>>{
        val userId = getCurrentUserUseCase()?.uid
            ?: throw IllegalStateException("User must be logged in")
        return orderRepository.getAllOrdersForCurrentUser(userId)
    }
}