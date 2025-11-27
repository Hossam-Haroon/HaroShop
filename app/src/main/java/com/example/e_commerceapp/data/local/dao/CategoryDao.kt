package com.example.e_commerceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.e_commerceapp.data.local.data.CategoryDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Upsert
    suspend fun upsertAllCategories(categories:List<CategoryDbEntity>)

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<CategoryDbEntity>>

    @Query("SELECT * FROM categories ORDER BY productCount DESC LIMIT 4")
    fun getSampleCategories():Flow<List<CategoryDbEntity>>
}