package com.example.e_commerceapp.data.mappers

import com.example.e_commerceapp.data.remote.data.CategoryEntity
import com.example.e_commerceapp.domain.model.Category

fun CategoryEntity.toDomain(): Category {
    return Category(
        categoryId, categoryName, productCount, thumbnailSampleImagesId
    )
}
fun Category.toEntity(): CategoryEntity {
    return CategoryEntity(
        categoryId, categoryName, productCount, thumbnailSampleImagesId
    )
}

fun List<Category>.toEntity():List<CategoryEntity> = this.map { it.toEntity() }
fun List<CategoryEntity>.toDomain():List<Category> = this.map { it.toDomain() }