package com.example.e_commerceapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.core.Utils.BACK4APP_PRODUCTS_CLASS
import com.example.e_commerceapp.domain.model.Category
import com.example.e_commerceapp.domain.usecases.categoryUseCases.GetAllCategoriesUseCase
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetUploadedImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllCategoriesScreenViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getUploadedImageUseCase: GetUploadedImageUseCase
):ViewModel() {
    private var _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()
    private var _ImagesUrl = MutableStateFlow<Map<String,List<String>>>(emptyMap())
    val imagesUrl = _ImagesUrl.asStateFlow()


    fun getAllCategories(){
        viewModelScope.launch {
            getAllCategoriesUseCase().catch {
                _categories.value = emptyList()
                Log.d("checkCategories","${_categories.value}")
            }.collect{categories ->
                _categories.value = categories
                categories.forEach { category->
                    launch {
                        val imagesUrl =  category.thumbnailSampleImagesId.map {
                            async {
                                getUploadedImageUseCase(
                                    it, BACK4APP_PRODUCTS_CLASS
                                ) ?: ""
                            }
                        }.awaitAll()
                        _ImagesUrl.update { current ->
                            current + (category.categoryId to imagesUrl)
                        }
                    }
                }

                Log.d("checkCategories","${_categories.value}")
            }
        }
    }
}