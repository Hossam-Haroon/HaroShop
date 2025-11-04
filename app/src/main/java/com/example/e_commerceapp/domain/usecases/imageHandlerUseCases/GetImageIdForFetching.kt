package com.example.e_commerceapp.domain.usecases.imageHandlerUseCases

import com.example.e_commerceapp.domain.repositories.ImageHandlingInDatabaseRepository
import javax.inject.Inject

class GetImageIdForFetching @Inject constructor(
    private val imageHandlingInDatabaseRepository: ImageHandlingInDatabaseRepository
) {
    suspend operator fun invoke(
        userId:String,
        collectionName:String,
        fieldName:String,
        back4appClassName : String
    ):String?{
        return imageHandlingInDatabaseRepository.getImageIdForFetching(
            userId,
            collectionName,
            fieldName,
            back4appClassName
        )
    }
}