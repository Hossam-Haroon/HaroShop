package com.example.e_commerceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.e_commerceapp.data.local.data.WishListProductDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WishListProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(wishListProductDbEntity: WishListProductDbEntity)

    @Query("SELECT * FROM wishListProducts WHERE toBeDeleted = 0")
    fun getAllFavouriteProductsIds():Flow<List<WishListProductDbEntity>>

    @Query("SELECT * FROM wishListProducts WHERE isSynced = 0 AND toBeDeleted = 0")
    suspend fun getUnSyncedProductsIds():List<WishListProductDbEntity>

    @Query("SELECT * FROM wishListProducts WHERE toBeDeleted = 1")
    suspend fun getProductsIdsToBeDeleted():List<WishListProductDbEntity>

    @Query("UPDATE wishListProducts SET isSynced = 1 WHERE productId = :productId")
    suspend fun markProductIdAsSynced(productId:String)

    @Delete
    suspend fun deleteProductId(wishListProductDbEntity: WishListProductDbEntity)

    @Query("SELECT * FROM wishListProducts WHERE productId = :productId AND toBeDeleted = 0")
    fun observeLikeState(productId: String): Flow<WishListProductDbEntity?>
}