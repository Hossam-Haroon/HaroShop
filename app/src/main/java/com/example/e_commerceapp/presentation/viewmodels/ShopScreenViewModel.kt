package com.example.e_commerceapp.presentation.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.core.Utils.BACK4APP_PRODUCTS_CLASS
import com.example.e_commerceapp.domain.model.Category
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.repositories.ProductRepository
import com.example.e_commerceapp.domain.usecases.categoryUseCases.GetSampleCategoriesUseCase
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetUploadedImageUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetSampleFlashSaleProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetMostPopularProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetNewestTenProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetProductDataUsingImageIdUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.JustForYouProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class ShopScreenViewModel @Inject constructor(
    private val getSampleCategoriesUseCase: GetSampleCategoriesUseCase,
    private val getNewestTenProductsUseCase:GetNewestTenProductsUseCase,
    private val getFlashSaleProductsUseCase:GetSampleFlashSaleProductsUseCase,
    private val getMostPopularProductsUseCase:GetMostPopularProductsUseCase,
    private val justForYouProductsUseCase: JustForYouProductsUseCase,
): ViewModel() {
    val categories : StateFlow<List<Category>> = getSampleCategoriesUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    val newProducts : StateFlow<List<Product>> = getNewestTenProductsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    val flashSaleProducts : StateFlow<List<Product>> = getFlashSaleProductsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    val popularProducts : StateFlow<List<Product>> = getMostPopularProductsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    val justForYouProducts : StateFlow<List<Product>> = justForYouProductsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}