package com.example.e_commerceapp.domain.usecases.userUseCases

import com.example.e_commerceapp.domain.model.User
import com.example.e_commerceapp.domain.repositories.UserRepository
import javax.inject.Inject

class GetUserByEmailUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(email:String): User? {
        return userRepository.getUserByEmail(email)
    }
}