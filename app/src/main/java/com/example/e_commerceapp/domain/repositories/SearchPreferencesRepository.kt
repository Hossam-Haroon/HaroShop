package com.example.e_commerceapp.domain.repositories

import kotlinx.coroutines.flow.Flow

interface SearchPreferencesRepository {
    val searchHistory : Flow<List<String>>
    suspend fun addSearchKeyword(keyword:String)
    suspend fun clearSearchHistory()
}