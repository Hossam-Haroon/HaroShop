package com.example.e_commerceapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserAddressUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserEmailUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserPhoneNumberUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.UpdateUserAddressAndPhoneNumberUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.UpdateUserAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactInformationViewModel @Inject constructor(
    private val getUserAddressUseCase: GetUserAddressUseCase,
    private val updateUserAddressUseCase: UpdateUserAddressUseCase,
    private val getUserPhoneNumberUseCase: GetUserPhoneNumberUseCase,
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val updateUserAddressAndPhoneNumberUseCase: UpdateUserAddressAndPhoneNumberUseCase,
): ViewModel() {
    private var _userAddress = MutableStateFlow("")
    val userAddress = _userAddress.asStateFlow()
    private var _userPhoneNumber = MutableStateFlow("")
    val userPhoneNumber = _userPhoneNumber.asStateFlow()
    private var _userEmail = MutableStateFlow("")
    val userEmail = _userEmail.asStateFlow()

    fun getUserAddress() {
        viewModelScope.launch {
            getUserAddressUseCase().collect{address ->
                _userAddress.value = address
            }
        }
    }

    fun getUserPhoneNumber(){
        viewModelScope.launch {
            getUserPhoneNumberUseCase().collect{phoneNumber ->
                _userPhoneNumber.value = phoneNumber
            }
        }
    }

    fun getUserEmail(){
        viewModelScope.launch {
            getUserEmailUseCase().collect{email ->
                _userEmail.value = email
            }
        }
    }

    fun updateUserAddress(address: String) {
        viewModelScope.launch {
            updateUserAddressUseCase(address)
        }
    }

    fun updateUserContactInformation(email:String,phoneNumber:String){
        viewModelScope.launch {
            updateUserAddressAndPhoneNumberUseCase(email, phoneNumber)
        }
    }
}