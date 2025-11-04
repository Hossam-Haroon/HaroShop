package com.example.e_commerceapp.di.usecases

import com.example.e_commerceapp.domain.repositories.ImageHandlingInDatabaseRepository
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetImageIdForFetching
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetUploadedImageUseCase
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.UploadImageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageHandlingUseCasesModule {
    @Provides
    @Singleton
    fun getImageIdForFetching(
        imageHandlingInDatabaseRepository: ImageHandlingInDatabaseRepository
    ) :GetImageIdForFetching{
        return GetImageIdForFetching(imageHandlingInDatabaseRepository)
    }
    @Provides
    @Singleton
    fun getUploadedImage(
        imageHandlingInDatabaseRepository: ImageHandlingInDatabaseRepository
    ) = GetUploadedImageUseCase(imageHandlingInDatabaseRepository)
    @Provides
    @Singleton
    fun getUploadImage(
        imageHandlingInDatabaseRepository: ImageHandlingInDatabaseRepository
    ) = UploadImageUseCase(imageHandlingInDatabaseRepository)

}