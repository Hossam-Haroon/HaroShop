package com.example.e_commerceapp.domain.usecases.cartUseCases

import com.example.e_commerceapp.domain.model.Cart
import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.CartRepository
import javax.inject.Inject

class UpdateCartAmountUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(cartProduct:Cart,amount:Int){
        val userId  = authenticationRepository.getCurrentUser()?.uid
            ?: throw IllegalStateException("User must be logged in")
        cartRepository.updateCartQuantity(userId,cartProduct,amount)
    }
}