package com.example.e_commerceapp.domain.usecases.userUseCases

import com.example.e_commerceapp.core.Utils.USER_EMAIL
import com.example.e_commerceapp.core.Utils.USER_PHONE_NUMBER
import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.UserRepository
import javax.inject.Inject

class UpdateUserAddressAndPhoneNumberUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(email: String, phoneNumber: String) {
        val userId = authenticationRepository.getCurrentUser()?.uid
            ?: throw IllegalStateException("User must be logged in")
        userRepository.updateUser(
            userId,
            mapOf(
                USER_EMAIL to email,
                USER_PHONE_NUMBER to phoneNumber
            )
        )
    }
}