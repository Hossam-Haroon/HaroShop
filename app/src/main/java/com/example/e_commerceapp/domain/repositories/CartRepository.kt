package com.example.e_commerceapp.domain.repositories

import android.content.Context
import com.example.e_commerceapp.domain.model.Cart
import com.example.e_commerceapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getAllCartItems(userId: String): Flow<List<Cart>>
    suspend fun insertProductToCart(
        product: Product,
        productAmount: Int,
        productColor: Long,
        productSize: String,
        userId: String,
        category:String
    )

    suspend fun deleteItemFromCart(
        cartProduct: Cart,
        userId: String
    )
    suspend fun updateCartQuantity(userId: String,cartProduct: Cart,quantity:Int)
    suspend fun deleteAllProductsInCart(userId:String):Result<Unit>
}