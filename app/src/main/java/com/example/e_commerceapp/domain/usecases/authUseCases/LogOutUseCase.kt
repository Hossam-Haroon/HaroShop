package com.example.e_commerceapp.domain.usecases.authUseCases

import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val authRepository:AuthenticationRepository) {
    operator fun invoke(){
        authRepository.logOut()
    }
}