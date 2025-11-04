package com.example.e_commerceapp.domain.usecases.orderUseCases

import com.example.e_commerceapp.domain.model.Order
import com.example.e_commerceapp.domain.repositories.OrderRepository
import com.example.e_commerceapp.domain.usecases.authUseCases.GetCurrentUserUseCase
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) {
    suspend operator fun invoke(order:Order):Result<Unit>{
        val userId = getCurrentUserUseCase()?.uid
            ?: throw IllegalStateException("User must be logged in")
        return orderRepository.createOrder(order, userId)
    }
}