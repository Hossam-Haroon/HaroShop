package com.example.e_commerceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.e_commerceapp.data.local.data.OrderDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Upsert
    suspend fun upsertAllOrders(orders:List<OrderDbEntity>)

    @Query("SELECT * FROM orders")
    fun getAllOrders(): Flow<List<OrderDbEntity>>

    @Query("DELETE FROM orders")
    suspend fun deleteAllOrders()
}