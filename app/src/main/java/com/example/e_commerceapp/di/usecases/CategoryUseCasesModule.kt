package com.example.e_commerceapp.di.usecases

import com.example.e_commerceapp.domain.repositories.CategoryRepository
import com.example.e_commerceapp.domain.usecases.categoryUseCases.GetAllCategoriesUseCase
import com.example.e_commerceapp.domain.usecases.categoryUseCases.GetSampleCategoriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoryUseCasesModule {
    @Provides
    @Singleton
    fun getAllCategories(categoryRepository: CategoryRepository) =
        GetAllCategoriesUseCase(categoryRepository)

    @Provides
    @Singleton
    fun getSampleCategories(categoryRepository: CategoryRepository) =
        GetSampleCategoriesUseCase(categoryRepository)
}