package com.example.e_commerceapp.domain.repositories

import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.model.WishListProduct
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun createProduct(product: Product)
    suspend fun updateProduct(productId: String,data:Map<String,Any>)
    fun getProductById(productId: String):Flow<Product?>
    fun getProductByCategory(productType:String): Flow<List<Product>>
    fun getFlashSaleProducts(limit: Long?):Flow<List<Product>>
    fun getNewestTenProducts():Flow<List<Product>>
    fun getMostPopularProducts():Flow<List<Product>>
    fun getStoryProducts():Flow<List<Product>>
    fun justForYouProducts():Flow<List<Product>>
    fun getProductsWithSpecificDiscount(discountValue :String):Flow<List<Product>>
    fun getSearchedProducts(keyword:String): Flow<List<Product>>
    suspend fun likeProduct(productId: String)
    suspend fun unlikeProduct(productId: String)
    fun isProductLiked(productId: String):Flow<Boolean>
    fun getALlWishListProductsIds():Flow<List<WishListProduct>>
}