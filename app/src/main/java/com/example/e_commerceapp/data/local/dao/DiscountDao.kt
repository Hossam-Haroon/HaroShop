package com.example.e_commerceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.e_commerceapp.data.local.data.DiscountDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DiscountDao {

    @Upsert
    suspend fun upsertAllDiscountValues(discounts:List<DiscountDbEntity>)

    @Query("SELECT * FROM discounts ORDER BY discountValue DESC")
    fun getAllDiscountValues(): Flow<List<DiscountDbEntity>>
}