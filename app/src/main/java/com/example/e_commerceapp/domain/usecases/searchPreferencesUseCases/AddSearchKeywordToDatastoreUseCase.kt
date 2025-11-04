package com.example.e_commerceapp.domain.usecases.searchPreferencesUseCases

import com.example.e_commerceapp.domain.repositories.SearchPreferencesRepository
import javax.inject.Inject

class AddSearchKeywordToDatastoreUseCase @Inject constructor(
    private val searchPreferencesRepository: SearchPreferencesRepository
) {
    suspend operator fun invoke(keyword: String) =
        searchPreferencesRepository.addSearchKeyword(keyword)
}