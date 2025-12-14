package com.example.e_commerceapp.domain.usecases.authUseCases

import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.OrderRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val authRepository:AuthenticationRepository
    ) {
    suspend operator fun invoke(){
        authRepository.logOut()
    }
}