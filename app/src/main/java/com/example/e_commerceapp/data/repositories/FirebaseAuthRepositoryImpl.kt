package com.example.e_commerceapp.data.repositories

import com.example.e_commerceapp.data.local.dataSources.LocalOrderDataSource
import com.example.e_commerceapp.data.local.dataSources.LocalUserDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteAuthDataSource
import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.model.HaroShopException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val localUserDataSource: LocalUserDataSource,
    private val localOrderDataSource: LocalOrderDataSource
): AuthenticationRepository {
    override suspend fun logIn(email: String, password: String): Result<FirebaseUser> {
        return try {
            val user = remoteAuthDataSource.logIn(email, password)
            Result.success(user)
        }catch (e: HaroShopException.UnableToLogInException){
            Result.failure(e)
        }
    }
    override suspend fun signUp(
        email: String,
        password: String
    ):Result<FirebaseUser> {
        return try {
            val user = remoteAuthDataSource.signUp(email,password)
            Result.success(user)
        }catch (e: FirebaseAuthUserCollisionException) {
            Result.failure(HaroShopException.ValidationException("Email already exists"))
        } catch (e: FirebaseAuthWeakPasswordException) {
            Result.failure(HaroShopException.ValidationException("Password is too weak"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(HaroShopException.ValidationException("Invalid email format"))
        } catch (e: FirebaseNetworkException) {
            Result.failure(HaroShopException.NetworkException("No internet connection"))
        } catch (e: FirebaseAuthException) {
            Result.failure(HaroShopException.UnableToSignUpException(
                "Authentication failed: ${e.message}"
            ))
        } catch (e: Exception) {
            Result.failure(HaroShopException.UnableToSignUpException(
                "Unexpected error occurred: ${e.message}"
            ))
        }
    }
    override suspend fun logOut() {
        remoteAuthDataSource.logOut()
        localUserDataSource.deleteCurrentUser()
        localOrderDataSource.deleteAllOrders()
    }
    override fun getCurrentUser() = remoteAuthDataSource.getCurrentUser()
}