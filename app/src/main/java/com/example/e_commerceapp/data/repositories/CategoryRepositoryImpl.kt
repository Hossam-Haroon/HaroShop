package com.example.e_commerceapp.data.repositories

import com.example.e_commerceapp.data.local.data.CartDbEntity
import com.example.e_commerceapp.data.local.data.CategoryDbEntity
import com.example.e_commerceapp.data.mappers.toDomain
import com.example.e_commerceapp.data.local.dataSources.LocalCategoryDataSource
import com.example.e_commerceapp.data.mappers.toDbEntity
import com.example.e_commerceapp.data.remote.dataSources.RemoteCategoryDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteImageDataSource
import com.example.e_commerceapp.domain.model.Category
import com.example.e_commerceapp.domain.repositories.CategoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val remoteCategoryDataSource: RemoteCategoryDataSource,
    private val localCategoryDataSource: LocalCategoryDataSource,
    private val remoteImageDataSource: RemoteImageDataSource
) : CategoryRepository {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    init {
        syncBackgroundData()
    }
    private fun syncBackgroundData(){
        scope.launch {
            remoteCategoryDataSource.getCategoriesStream().collect{categoriesEntities ->
                val imagesIds = categoriesEntities.flatMap {
                    it.thumbnailSampleImagesId
                }
                val imagesMap = remoteImageDataSource.getImageUrls(imagesIds)
                val categoriesDbEntities = categoriesEntities.map { categoryNetworkEntity ->
                    val imagesUrl = categoryNetworkEntity.thumbnailSampleImagesId.mapNotNull{
                        imagesMap[it]?.imageUrl
                    }
                    categoryNetworkEntity.copy(thumbnailSampleImagesId = imagesUrl).toDbEntity()
                }
                localCategoryDataSource.upsertAllCategories(categoriesDbEntities)
            }
        }
    }
    override fun getCategoriesList(): Flow<List<Category>> {
        return localCategoryDataSource.getALlCategories().map { categories ->
            categories.toDomain()
        }
    }
    override fun getSampleCategories(): Flow<List<Category>> {
        return localCategoryDataSource.getSampleCategories().map {
            it.toDomain()
        }
    }
}