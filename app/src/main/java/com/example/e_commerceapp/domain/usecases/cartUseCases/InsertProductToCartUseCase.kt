package com.example.e_commerceapp.domain.usecases.cartUseCases

import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.CartRepository
import javax.inject.Inject

class InsertProductToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(
        product: Product,
        amount:Int,
        color:Long,
        size:String,
        category:String
    ) {
        val userId  = authenticationRepository.getCurrentUser()?.uid
            ?: throw IllegalStateException("User must be logged in")
        cartRepository.insertProductToCart(product, amount, color, size,userId,category)
    }
}