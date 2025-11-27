package com.example.e_commerceapp.data.remote.dataSources

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteAuthDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun logIn(email: String, password: String): FirebaseUser {
        val result = firebaseAuth
            .signInWithEmailAndPassword(email, password)
            .await()
        return result.user!!
    }

    suspend fun signUp(
        email: String,
        password: String
    ): FirebaseUser {
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        return result.user!!
    }

    fun logOut() = firebaseAuth.signOut()
    fun getCurrentUser() = firebaseAuth.currentUser
}