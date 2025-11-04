package com.example.e_commerceapp.domain.usecases.authUseCases

import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import javax.inject.Inject

class LogInUseCase @Inject constructor(private val authRepository: AuthenticationRepository){
    suspend operator fun invoke(email:String, password:String){
        authRepository.logIn(email, password)
    }
}