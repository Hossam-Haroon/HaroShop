package com.example.e_commerceapp.domain.repositories

import com.example.e_commerceapp.domain.model.Product
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun createProduct(product: Product)
    suspend fun updateProduct(productId: String,data:Map<String,Any>)
    suspend fun getProductById(productId: String):Product?
    fun getProductByCategory(productType:String): Flow<List<Product>>
    fun getFlashSaleProducts(limit: Long?):Flow<List<Product>>
    fun getNewestTenProducts():Flow<Result<List<Pair<String,String>>>>
    suspend fun getProductDataUsingImageId(imageId:String):Result<Product?>
    suspend fun toggleFavouriteProducts(productId: String,userRef: DocumentReference)
    fun getMostPopularProducts():Flow<List<Product>>
    fun getStoryProducts():Flow<List<Product>>
    fun justForYouProducts():Flow<List<Product>>
    fun getProductsWithSpecificDiscount(discountValue :String):Flow<List<Product>>
    suspend fun getSearchedProducts(keyword:String): List<Product>
}