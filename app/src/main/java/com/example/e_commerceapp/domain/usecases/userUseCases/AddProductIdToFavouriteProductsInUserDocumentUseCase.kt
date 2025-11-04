package com.example.e_commerceapp.domain.usecases.userUseCases

import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.UserRepository
import javax.inject.Inject

class AddProductIdToFavouriteProductsInUserDocumentUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(productId:String){
        val userId = authenticationRepository.getCurrentUser()?.uid
            ?: throw IllegalStateException("User must be logged in")
        return userRepository.addProductIdToFavouriteProductsInUserDocument(productId,userId)
    }
}