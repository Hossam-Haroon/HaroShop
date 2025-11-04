package com.example.e_commerceapp.domain.usecases.userUseCases

import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsProductLikedUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(productId: String): Flow<Boolean> {
        val userId = authenticationRepository.getCurrentUser()?.uid
            ?: throw IllegalStateException("User must be logged in")
        return userRepository.isProductLiked(productId, userId)
    }
}