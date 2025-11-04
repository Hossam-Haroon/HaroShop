package com.example.e_commerceapp.domain.usecases.authUseCases

import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository : AuthenticationRepository
) {
    operator fun invoke():FirebaseUser?{
        return authRepository.getCurrentUser()
    }
}