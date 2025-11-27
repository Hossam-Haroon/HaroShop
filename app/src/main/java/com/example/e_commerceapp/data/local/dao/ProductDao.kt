package com.example.e_commerceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.e_commerceapp.data.local.data.ProductDbEntity
import com.example.e_commerceapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Upsert
    suspend fun upsertProducts(products: List<ProductDbEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProduct(product:ProductDbEntity)

    @Query("SELECT * FROM products")
    fun getAllProducts():Flow<List<ProductDbEntity>>

    @Query("SELECT * FROM products WHERE favouriteCount > 0 ORDER BY favouriteCount DESC LIMIT 10")
    fun getPopularProducts(): Flow<List<ProductDbEntity>>

    @Query("SELECT * FROM products WHERE productType = :categoryName")
    fun getProductsForCategory(categoryName: String): Flow<List<ProductDbEntity>>

    @Query("SELECT * FROM products WHERE productId = :productId")
    fun getProductById(productId:String):Flow<ProductDbEntity?>

    @Query("SELECT * FROM products WHERE discount > 0 ORDER BY discount DESC LIMIT :limit")
    fun getFlashSaleProducts(limit:Long):Flow<List<ProductDbEntity>>

    @Query("SELECT * FROM products WHERE discount > 0 ORDER BY discount DESC")
    fun getAllFlashSaleProducts(): Flow<List<ProductDbEntity>>

    @Query("SELECT * FROM products ORDER BY createdAt DESC LIMIT 10")
    fun getNewestTenProducts(): Flow<List<ProductDbEntity>>

    @Query("SELECT * FROM products LIMIT :limit")
    fun getProductsWithLimit(limit:Long):Flow<List<ProductDbEntity>>

    @Query("SELECT * FROM products WHERE discount = :discountValue")
    fun getProductsWithSpecificDiscountValue(discountValue:Int):Flow<List<ProductDbEntity>>

    @Query("SELECT * FROM products WHERE productName LIKE :keyword OR description LIKE :keyword")
    fun getSearchedProducts(keyword:String):Flow<List<ProductDbEntity>>
}