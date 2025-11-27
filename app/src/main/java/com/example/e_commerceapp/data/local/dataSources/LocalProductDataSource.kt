package com.example.e_commerceapp.data.local.dataSources

import com.example.e_commerceapp.data.local.dao.ProductDao
import com.example.e_commerceapp.data.local.data.ProductDbEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalProductDataSource @Inject constructor(
    private val productDao: ProductDao
) {
    fun getPopularProducts() = productDao.getPopularProducts()
    fun getProductsWithLimit(limit:Long) = productDao.getProductsWithLimit(limit)
    fun getSearchedProducts(keyword:String): Flow<List<ProductDbEntity>> {
        val searchQuery = "%${keyword}%"
        return productDao.getSearchedProducts(searchQuery)
    }
    fun getProductsWithSpecificDiscountValue(discount:Int):Flow<List<ProductDbEntity>> {
        return productDao.getProductsWithSpecificDiscountValue(discount)
    }
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