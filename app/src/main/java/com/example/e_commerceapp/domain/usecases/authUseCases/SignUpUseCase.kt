package com.example.e_commerceapp.domain.usecases.authUseCases

import com.example.e_commerceapp.domain.model.HaroShopException
import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val authRepository:AuthenticationRepository) {
    suspend operator fun invoke(
        email:String,
        password:String,
    ):Result<FirebaseUser>{
        return when{
            email.isEmpty() ->
                Result.failure(HaroShopException.ValidationException("please enter an email!"))
            password.isEmpty() ->
                Result.failure(HaroShopException.ValidationException("please enter a password!"))
            password.length < 8 ->
                Result.failure(
                    HaroShopException.ValidationException("please enter a valid password!")
                )
            !isValidEmail(email) ->
                Result.failure(HaroShopException.ValidationException("please enter a valid email"))
            else -> {
                authRepository.signUp(email,password)
            }
        }
    }
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}