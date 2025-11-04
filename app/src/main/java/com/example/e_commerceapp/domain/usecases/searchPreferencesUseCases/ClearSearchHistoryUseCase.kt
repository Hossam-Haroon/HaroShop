package com.example.e_commerceapp.domain.usecases.searchPreferencesUseCases

import com.example.e_commerceapp.domain.repositories.SearchPreferencesRepository
import javax.inject.Inject

class ClearSearchHistoryUseCase @Inject constructor(
    private val searchPreferencesRepository: SearchPreferencesRepository
) {
    suspend operator fun invoke() = searchPreferencesRepository.clearSearchHistory()
}