package com.example.e_commerceapp.domain.usecases.userUseCases

import com.example.e_commerceapp.core.Utils.USER_ADDRESS
import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.UserRepository
import javax.inject.Inject

class UpdateUserAddressUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(userAddress:String){
        val userId  = authenticationRepository.getCurrentUser()?.uid
            ?: throw IllegalStateException("User must be logged in")
        userRepository.updateUser(userId, mapOf(USER_ADDRESS to userAddress))
    }
}