package com.example.e_commerceapp.di.usecases

import com.example.e_commerceapp.domain.repositories.SearchPreferencesRepository
import com.example.e_commerceapp.domain.usecases.searchPreferencesUseCases.AddSearchKeywordToDatastoreUseCase
import com.example.e_commerceapp.domain.usecases.searchPreferencesUseCases.ClearSearchHistoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchPreferencesUseCasesModule {
    @Provides
    @Singleton
    fun getSearchHistoryUseCase(
        searchPreferencesRepository: SearchPreferencesRepository
    ) = AddSearchKeywordToDatastoreUseCase(searchPreferencesRepository)

    @Provides
    @Singleton
    fun getClearSearchHistory(
        searchPreferencesRepository: SearchPreferencesRepository
    ) = ClearSearchHistoryUseCase(searchPreferencesRepository)
}