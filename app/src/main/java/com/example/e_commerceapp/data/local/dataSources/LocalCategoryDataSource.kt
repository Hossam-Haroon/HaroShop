package com.example.e_commerceapp.data.local.dataSources

import com.example.e_commerceapp.data.local.dao.CategoryDao
import com.example.e_commerceapp.data.local.data.CategoryDbEntity
import javax.inject.Inject

class LocalCategoryDataSource @Inject constructor(
    private val categoryDao: CategoryDao
) {
    suspend fun upsertAllCategories(categories:List<CategoryDbEntity>) =
        categoryDao.upsertAllCategories(categories)

    fun getALlCategories() = categoryDao.getAllCategories()

    fun getSampleCategories() = categoryDao.getSampleCategories()
}