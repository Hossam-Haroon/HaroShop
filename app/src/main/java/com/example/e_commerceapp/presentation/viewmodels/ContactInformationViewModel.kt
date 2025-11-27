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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactInformationViewModel @Inject constructor(
    getUserAddressUseCase: GetUserAddressUseCase,
    private val updateUserAddressUseCase: UpdateUserAddressUseCase,
    getUserPhoneNumberUseCase: GetUserPhoneNumberUseCase,
    getUserEmailUseCase: GetUserEmailUseCase,
    private val updateUserAddressAndPhoneNumberUseCase: UpdateUserAddressAndPhoneNumberUseCase,
) : ViewModel() {
    val userAddress: StateFlow<String> = getUserAddressUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ""
    )
    val userPhoneNumber: StateFlow<String> = getUserPhoneNumberUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ""
    )
    val userEmail: StateFlow<String> = getUserEmailUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ""
    )

    fun updateUserAddress(address: String) {
        viewModelScope.launch {
            updateUserAddressUseCase(address)
        }
    }

    fun updateUserContactInformation(email: String, phoneNumber: String) {
        viewModelScope.launch {
            updateUserAddressAndPhoneNumberUseCase(email, phoneNumber)
        }
    }
}