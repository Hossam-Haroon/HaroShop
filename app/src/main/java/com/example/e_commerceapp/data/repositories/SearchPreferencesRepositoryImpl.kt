package com.example.e_commerceapp.data.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.e_commerceapp.data.local.searchDataStore
import com.example.e_commerceapp.domain.repositories.SearchPreferencesRepository
import kotlinx.coroutines.flow.map

class SearchPreferencesRepositoryImpl(private val context:Context): SearchPreferencesRepository {


    override val searchHistory = context.searchDataStore.data.map { preferences ->
        preferences[SEARCH_HISTORY_KEY]?.toList()?.reversed() ?: emptyList()
    }

    override suspend fun addSearchKeyword(keyword: String) {
        if (keyword.isBlank()) return
        context.searchDataStore.edit { preferences->
            val currentSet = preferences[SEARCH_HISTORY_KEY]?.toMutableList() ?: mutableListOf()
            currentSet.remove(keyword.lowercase())
            currentSet.add(0,keyword.lowercase())
            if (currentSet.size > MAX_HISTORY_SIZE){
                currentSet.removeLast()
            }
            preferences[SEARCH_HISTORY_KEY] = currentSet.toSet()
        }
    }

    override suspend fun clearSearchHistory() {
        context.searchDataStore.edit { it.remove(SEARCH_HISTORY_KEY) }
    }

    companion object{
        private val SEARCH_HISTORY_KEY = stringSetPreferencesKey("search_history")
        private const val MAX_HISTORY_SIZE = 5
    }
}