package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.domain.usecases.authUseCases.GetCurrentUserUseCase
import com.example.e_commerceapp.domain.usecases.authUseCases.LogInUseCase
import com.example.e_commerceapp.domain.usecases.authUseCases.LogOutUseCase
import com.example.e_commerceapp.domain.usecases.authUseCases.SignUpUseCase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val logInUseCase: LogInUseCase,
    private val signUpUseCase: SignUpUseCase
): ViewModel(){
    private var _userState  = MutableStateFlow<Result<FirebaseUser>?>(null)
    val userState = _userState.asStateFlow()
    private var _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()
    fun logOutUser(){
        viewModelScope.launch { logOutUseCase() }
    }
    fun logInUser(email: String,password: String){
        viewModelScope.launch { logInUseCase(email,password) }
    }
    fun signUpUser(email: String,password: String){
        viewModelScope.launch {
            _isLoading.value = true
           val result = signUpUseCase(email, password)
            _userState.value = result
            _isLoading.value = false
        }
    }
    fun getCurrentUser():FirebaseUser?{
         return getCurrentUserUseCase()
    }
    fun validatePassword(password:String, onNavigate:()->Unit,onError:(String)->Unit){
        when{
            password.isEmpty() -> onError("please enter a password!")
            password.length < 8 -> onError("please enter a valid password!")
            else -> {
                onNavigate()
            }
        }
    }
    fun validateEmail(email:String, onNavigate:()->Unit,onError:(String)->Unit){
        when{
            email.isEmpty() -> onError("please enter an email!")
            else -> {
                onNavigate()
            }
        }
    }
}