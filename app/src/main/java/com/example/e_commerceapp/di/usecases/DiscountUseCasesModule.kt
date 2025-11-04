package com.example.e_commerceapp.di.usecases

import com.example.e_commerceapp.domain.repositories.DiscountRepository
import com.example.e_commerceapp.domain.usecases.discountUseCases.GetAllDiscountValuesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiscountUseCasesModule {
    @Provides
    @Singleton
    fun getAllDiscountValues(
        discountRepository: DiscountRepository
    ) = GetAllDiscountValuesUseCase(discountRepository)
}