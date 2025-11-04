package com.example.e_commerceapp.presentation.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.core.Utils.BACK4APP_USER_CLASS
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetImageIdForFetching
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetUploadedImageUseCase
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.UploadImageUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserByIdUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.UpdateUserProfileSettingsDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileSettingsScreenViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getImageIdForFetching: GetImageIdForFetching,
    private val uploadImageUseCase: UploadImageUseCase,
    private val getUploadedImageUseCase: GetUploadedImageUseCase,
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
            user?.let {
                _userName.value = it.userName
                _userEmail.value = it.email
                _userPhoneNumber.value = it.phoneNumber
            }
            val userImage = user?.imageUrl?.let {
                getUploadedImageUseCase(it, BACK4APP_USER_CLASS)
            }
            _imageUrl.value = userImage
            /*val imageUrl = user?.let {
                getImageIdForFetching(
                    it.userId,
                    USER,
                    USER_IMAGE_URL,
                    BACK4APP_USER_CLASS
                )
            }*/
        }
    }
}