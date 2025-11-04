package com.example.e_commerceapp.domain.usecases.imageHandlerUseCases

import com.example.e_commerceapp.domain.repositories.ImageHandlingInDatabaseRepository
import javax.inject.Inject

class GetUploadedImageUseCase @Inject constructor(
    private val imageHandlingInDatabaseRepository: ImageHandlingInDatabaseRepository
) {
    suspend operator fun invoke(
        imageId:String,
        back4appClassName:String
    ) = imageHandlingInDatabaseRepository.getUploadedImage(imageId,back4appClassName)
}