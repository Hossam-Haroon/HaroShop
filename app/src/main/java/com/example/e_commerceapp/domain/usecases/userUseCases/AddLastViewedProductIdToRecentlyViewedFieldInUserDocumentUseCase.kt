package com.example.e_commerceapp.domain.usecases.userUseCases

import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.UserRepository
import javax.inject.Inject

class AddLastViewedProductIdToRecentlyViewedFieldInUserDocumentUseCase
@Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(productId:String){
        userRepository.addLastViewedProductIdToRecentlyViewedFieldInUserDocument(productId)
    }
}