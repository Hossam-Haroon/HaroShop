package com.example.e_commerceapp.di.usecases

import com.example.e_commerceapp.domain.repositories.PaymentRepository
import com.example.e_commerceapp.domain.usecases.paymentUseCases.CreateCustomerIdUseCase
import com.example.e_commerceapp.domain.usecases.paymentUseCases.CreatePaymentIntentUseCase
import com.example.e_commerceapp.domain.usecases.paymentUseCases.CreateSetupIntentUseCase
import com.example.e_commerceapp.domain.usecases.paymentUseCases.GetListCardsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaymentUseCasesModule {
    @Provides
    @Singleton
    fun createCustomerIdUseCase(
        paymentRepository: PaymentRepository
    ) = CreateCustomerIdUseCase(paymentRepository)

    @Provides
    @Singleton
    fun createPaymentIntentUseCase(
        paymentRepository: PaymentRepository
    ) = CreatePaymentIntentUseCase(paymentRepository)

    @Provides
    @Singleton
    fun createSetupIntent(
        paymentRepository: PaymentRepository
    ) = CreateSetupIntentUseCase(paymentRepository)

    @Provides
    @Singleton
    fun getListCards(
        paymentRepository: PaymentRepository
    ) = GetListCardsUseCase(paymentRepository)
}