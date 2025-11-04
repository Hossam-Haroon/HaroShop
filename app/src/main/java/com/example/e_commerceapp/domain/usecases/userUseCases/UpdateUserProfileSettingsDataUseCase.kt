package com.example.e_commerceapp.domain.usecases.userUseCases

import com.example.e_commerceapp.core.Utils.USER_ADDRESS
import com.example.e_commerceapp.core.Utils.USER_EMAIL
import com.example.e_commerceapp.core.Utils.USER_IMAGE_URL
import com.example.e_commerceapp.core.Utils.USER_NAME
import com.example.e_commerceapp.core.Utils.USER_PHONE_NUMBER
import com.example.e_commerceapp.domain.repositories.UserRepository
import com.example.e_commerceapp.domain.usecases.authUseCases.GetCurrentUserUseCase
import javax.inject.Inject

class UpdateUserProfileSettingsDataUseCase @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        email: String,
        phoneNumber: String,
        userName: String,
        userImage: String
    ) {
        val userId = getCurrentUserUseCase()?.uid
            ?: throw IllegalStateException("User must be logged in")
        userRepository.updateUser(
            userId,
            mapOf(
                USER_EMAIL to email,
                USER_IMAGE_URL to userImage,
                USER_PHONE_NUMBER to phoneNumber,
                USER_NAME to userName
            )
        )
    }
}