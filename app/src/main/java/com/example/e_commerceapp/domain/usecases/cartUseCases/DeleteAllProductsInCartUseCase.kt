package com.example.e_commerceapp.domain.usecases.cartUseCases

import com.example.e_commerceapp.domain.repositories.CartRepository
import com.example.e_commerceapp.domain.usecases.authUseCases.GetCurrentUserUseCase
import javax.inject.Inject

class DeleteAllProductsInCartUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) {
    suspend operator fun invoke():Result<Unit>{
        val userId = getCurrentUserUseCase()?.uid
        ?: throw IllegalStateException("User must be logged in")
        return cartRepository.deleteAllProductsInCart(userId)
    }
}