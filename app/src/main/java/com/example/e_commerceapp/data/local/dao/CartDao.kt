package com.example.e_commerceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.e_commerceapp.data.local.data.CartDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Upsert
    suspend fun upsertAllCarts(carts:List<CartDbEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCart(cart:CartDbEntity)

    @Query("SELECT * FROM carts WHERE isDeleted = 0")
    fun getAllCarts(): Flow<List<CartDbEntity>>

    @Query("SELECT * FROM carts WHERE isSynced = 0 AND isDeleted = 0")
    suspend fun getUnSyncedCarts(): List<CartDbEntity>

    @Query("SELECT * FROM carts WHERE isDeleted = 1")
    suspend fun getDeletedItems():List<CartDbEntity>

    @Query("UPDATE carts SET isSynced = 1 WHERE productId = :productId")
    suspend fun markCartAsSynced(productId:String)

    @Delete
    suspend fun deleteItemPermanently(cart:CartDbEntity)
}