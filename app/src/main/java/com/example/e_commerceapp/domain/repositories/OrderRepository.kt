package com.example.e_commerceapp.domain.repositories

import com.example.e_commerceapp.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun createOrder(order: Order,userId:String):Result<Unit>
    fun getAllOrdersForCurrentUser(userId: String): Flow<List<Order>>
    suspend fun deleteAllOrders()
}