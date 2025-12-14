package com.example.e_commerceapp.presentation.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.domain.model.User
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.UploadImageUseCase
import com.example.e_commerceapp.domain.usecases.paymentUseCases.CreateCustomerIdUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.CreateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val createCustomerIdUseCase: CreateCustomerIdUseCase
):ViewModel() {
    private var _customerId = MutableStateFlow("")
    val customerId = _customerId.asStateFlow()
    fun createUserToFirebase(user: User){
        viewModelScope.launch {
            createUserUseCase(user)
        }
    }
    suspend fun uploadImage(imageUri:Uri, context: Context):String?{
        return  uploadImageUseCase(imageUri,context)
    }

    fun createCustomerIdForPaymentMethods(email:String){
        viewModelScope.launch {
            createCustomerIdUseCase(email).onSuccess { customerId->
                _customerId.value = customerId
            }.onFailure {
                _customerId.value = "Error making customerId From Stripe: ${it.message}"
            }
        }
    }
    suspend fun completeUserSetup(
        userAuthUid: String,
        email: String,
        userName: String,
        fullPhoneNumber: String,
        imageUri: Uri?,
        context: Context
    ): Result<Unit> {
        return try {
            val imageUrl = imageUri?.let {
                uploadImageUseCase(it, context)
            }
            val customerId = createCustomerIdUseCase(email).getOrThrow()
            val user = User(
                userId = userAuthUid,
                email = email,
                userName = userName,
                phoneNumber = fullPhoneNumber,
                favoriteProducts = emptyList(),
                imageUrl = imageUrl,
                stripeCustomerId = customerId,
                recentlyViewed = emptyList(),
                reviewsId = emptyList(),
                userAddress = "",
                accountRole = "user"
            )
            createUserUseCase(user)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}