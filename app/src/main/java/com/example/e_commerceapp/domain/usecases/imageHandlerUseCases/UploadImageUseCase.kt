package com.example.e_commerceapp.domain.usecases.imageHandlerUseCases

import android.content.Context
import android.net.Uri
import com.example.e_commerceapp.domain.repositories.ImageHandlingInDatabaseRepository
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val imageHandlingInDatabaseRepository: ImageHandlingInDatabaseRepository
){
    suspend operator fun invoke(imageUri: Uri,context: Context): String?{
        return imageHandlingInDatabaseRepository.uploadImage(imageUri, context)
    }
}