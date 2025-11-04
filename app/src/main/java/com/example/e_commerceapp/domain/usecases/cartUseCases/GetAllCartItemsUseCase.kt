package com.example.e_commerceapp.domain.usecases.cartUseCases

import com.example.e_commerceapp.domain.model.Cart
import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCartItemsUseCase @Inject constructor(
    private val cartRepository : CartRepository,
    private val authenticationRepository: AuthenticationRepository
 ) {
    operator fun invoke() : Flow<List<Cart>> {
        val userId  = authenticationRepository.getCurrentUser()?.uid
            ?: throw IllegalStateException("User must be logged in")
       return cartRepository.getAllCartItems(userId)
    }
}