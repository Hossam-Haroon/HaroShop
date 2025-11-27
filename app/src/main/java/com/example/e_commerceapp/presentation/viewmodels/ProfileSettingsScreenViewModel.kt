package com.example.e_commerceapp.presentation.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.core.Utils.BACK4APP_USER_CLASS
import com.example.e_commerceapp.domain.model.User
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetImageIdForFetching
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetUploadedImageUseCase
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.UploadImageUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserByIdUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.UpdateUserProfileSettingsDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileSettingsScreenViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val updateUserProfileSettingsDataUseCase: UpdateUserProfileSettingsDataUseCase
):ViewModel() {
    private var _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl = _imageUrl.asStateFlow()
    private var _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()
    private var _userEmail = MutableStateFlow("")
    val userEmail = _userEmail.asStateFlow()
    private var _userPhoneNumber = MutableStateFlow("")
    val userPhoneNumber = _userPhoneNumber.asStateFlow()

    val userData : StateFlow<User?> = flow {
        emit(getUserByIdUseCase())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = null
    )
    suspend fun uploadImage(imageUri: Uri, context: Context):String?{
        return  uploadImageUseCase(imageUri,context)
    }

    fun updateUserData(
        email: String,
        phoneNumber: String,
        userName: String,
        userImage: String?
    ){
        viewModelScope.launch {
            if (userImage != null) {
                updateUserProfileSettingsDataUseCase(email, phoneNumber, userName, userImage)
            }
        }
    }

    fun getUserProfileById() {
        viewModelScope.launch {
            val user = getUserByIdUseCase()
            Log.d("ProfileScreen","$user")
            user?.let {
                _userName.value = it.userName
                Log.d("ProfileScreen", _userName.value)
                _userEmail.value = it.email
                _userPhoneNumber.value = it.phoneNumber
                _imageUrl.value = it.imageUrl
            }
        }
    }
}