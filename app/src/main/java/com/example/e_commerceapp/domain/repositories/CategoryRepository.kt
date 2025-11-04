package com.example.e_commerceapp.domain.repositories

import com.example.e_commerceapp.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategoriesList(): Flow<List<Category>>
    fun getSampleCategories(): Flow<List<Category>>
}