package com.example.e_commerceapp.data.local.dataSources

import com.example.e_commerceapp.data.local.dao.ProductDao
import com.example.e_commerceapp.data.local.data.ProductDbEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalProductDataSource @Inject constructor(
    private val productDao: ProductDao
) {
    fun getPopularProducts() = productDao.getPopularProducts()
    fun getNewestTenProducts() = productDao.getNewestTenProducts()
    fun getCategoryProducts(category:String) = productDao.getProductsForCategory(category)
    fun getFlashSaleProducts(limit:Long?): Flow<List<ProductDbEntity>> {
        return if (limit == null){
            productDao.getAllFlashSaleProducts()
        }else{
            productDao.getFlashSaleProducts(limit)
        }
    }
    fun getProductById(productId:String) = productDao.getProductById(productId)
    suspend fun upsertProducts(products:List<ProductDbEntity>) = productDao.upsertProducts(products)
}