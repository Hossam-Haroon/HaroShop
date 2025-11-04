package com.example.e_commerceapp.domain.usecases.productUseCases

import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.ProductRepository
import com.example.e_commerceapp.domain.repositories.UserRepository
import javax.inject.Inject

class ToggleFavouriteProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository,
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(productId:String){
        val userId  = authenticationRepository.getCurrentUser()?.uid
            ?: throw IllegalStateException("User must be logged in")
        val userRef = userRepository.getDocumentRefOfTheUser(userId)
        if (userRef != null) {
            productRepository.toggleFavouriteProducts(productId,userRef)
        }
    }
}