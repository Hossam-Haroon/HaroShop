package com.example.e_commerceapp.domain.repositories

import com.google.firebase.auth.FirebaseUser

interface AuthenticationRepository {

    suspend fun logIn(email:String,password:String):Result<FirebaseUser>
    suspend fun signUp(email:String,password: String):Result<FirebaseUser>
    fun logOut()
    fun getCurrentUser():FirebaseUser?
}