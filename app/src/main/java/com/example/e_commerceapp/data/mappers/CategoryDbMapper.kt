package com.example.e_commerceapp.data.mappers

import com.example.e_commerceapp.data.local.data.CategoryDbEntity
import com.example.e_commerceapp.data.remote.data.CategoryEntity
import com.example.e_commerceapp.domain.model.Category

fun CategoryEntity.toDbEntity(): CategoryDbEntity {
    return CategoryDbEntity(
        categoryId, categoryName, productCount, thumbnailSampleImagesId
    )
}

fun CategoryDbEntity.toDomain(): Category {
    return Category(
        categoryId, categoryName, productCount, thumbnailSampleImagesId
    )
}

fun List<CategoryEntity>.toDbEntity():List<CategoryDbEntity> = this.map { it.toDbEntity() }
fun List<CategoryDbEntity>.toDomain():List<Category> = this.map { it.toDomain() }