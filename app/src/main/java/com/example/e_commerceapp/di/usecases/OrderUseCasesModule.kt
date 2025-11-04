package com.example.e_commerceapp.di.usecases

import com.example.e_commerceapp.domain.repositories.OrderRepository
import com.example.e_commerceapp.domain.usecases.authUseCases.GetCurrentUserUseCase
import com.example.e_commerceapp.domain.usecases.orderUseCases.CreateOrderUseCase
import com.example.e_commerceapp.domain.usecases.orderUseCases.GetAllOrderItemsUseCase
import com.example.e_commerceapp.domain.usecases.orderUseCases.GetAllOrdersForCurrentUserUseCase
import com.example.e_commerceapp.domain.usecases.orderUseCases.GetCategoriesTotalUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OrderUseCasesModule {

    @Provides
    @Singleton
    fun getAllOrders(
        orderRepository: OrderRepository,
        getCurrentUserUseCase: GetCurrentUserUseCase
    ) = GetAllOrdersForCurrentUserUseCase(orderRepository, getCurrentUserUseCase)

    @Provides
    @Singleton
    fun createOrder(
        orderRepository: OrderRepository,
        getCurrentUserUseCase: GetCurrentUserUseCase
    ) = CreateOrderUseCase(orderRepository, getCurrentUserUseCase)

    @Provides
    @Singleton
    fun getCategoryTotalsUseCase(
        getAllOrdersForCurrentUserUseCase: GetAllOrdersForCurrentUserUseCase
    ) = GetCategoriesTotalUseCase(getAllOrdersForCurrentUserUseCase)

    @Provides
    @Singleton
    fun getALlOrderItems(
        getAllOrdersForCurrentUserUseCase: GetAllOrdersForCurrentUserUseCase
    ) = GetAllOrderItemsUseCase(getAllOrdersForCurrentUserUseCase)
}