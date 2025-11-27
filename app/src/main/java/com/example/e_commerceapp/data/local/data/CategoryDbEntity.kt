package com.example.e_commerceapp.data.local.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryDbEntity(
    @PrimaryKey
    val categoryId: String,
    val categoryName: String,
    val productCount: Int,
    val thumbnailSampleImagesId: List<String>
)
