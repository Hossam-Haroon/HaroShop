package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.core.Utils.BACK4APP_PRODUCTS_CLASS
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.repositories.SearchPreferencesRepository
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetUploadedImageUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetSearchedProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.JustForYouProductsUseCase
import com.example.e_commerceapp.domain.usecases.searchPreferencesUseCases.AddSearchKeywordToDatastoreUseCase
import com.example.e_commerceapp.domain.usecases.searchPreferencesUseCases.ClearSearchHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getSearchedProductsUseCase: GetSearchedProductsUseCase,
    private val addSearchKeywordToDatastoreUseCase: AddSearchKeywordToDatastoreUseCase,
    private val clearSearchHistoryUseCase: ClearSearchHistoryUseCase,
    justForYouProductsUseCase: JustForYouProductsUseCase,
    searchPreferencesRepository: SearchPreferencesRepository
) : ViewModel() {
    private var _query = MutableStateFlow("")
    val query = _query.asStateFlow()
    private val _searchResults = MutableStateFlow<List<Product>>(emptyList())
    val searchResults = _searchResults.asStateFlow()
    val recentSearches = searchPreferencesRepository.searchHistory.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )
    val discoverSectionProducts : StateFlow<List<Product>> = justForYouProductsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    init {
        viewModelScope.launch {
            query
                .debounce(400)
                .filter { it.isNotEmpty() }
                .distinctUntilChanged()
                .collect {
                    searchProducts(it)
                    addSearchKeywordToDatastore(it)
                }
        }
    }

    private fun addSearchKeywordToDatastore(keyword: String) {
        viewModelScope.launch {
            addSearchKeywordToDatastoreUseCase(keyword)
        }
    }

    fun onChangeQuery(newValue: String) {
        _query.value = newValue
    }

    private fun searchProducts(query: String) {
        viewModelScope.launch {
            getSearchedProductsUseCase(query)
                .catch {
                    _searchResults.value = emptyList()
                }.collect{products ->
                _searchResults.value = products
            }

        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            clearSearchHistoryUseCase()
        }
    }
}