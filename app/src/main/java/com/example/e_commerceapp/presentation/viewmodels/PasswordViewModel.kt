package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserByEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val getUserByEmailUseCase: GetUserByEmailUseCase
):ViewModel() {
    private var _userName = MutableStateFlow<String?>(null)
    val userName = _userName.asStateFlow()

    fun fetchUserNameUsingEmail(email:String){
        viewModelScope.launch {
            val user = getUserByEmailUseCase(email)
            _userName.value = user?.userName
        }
    }
}