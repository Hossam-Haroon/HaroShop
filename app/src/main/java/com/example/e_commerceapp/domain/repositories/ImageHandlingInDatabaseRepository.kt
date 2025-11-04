package com.example.e_commerceapp.domain.repositories

import android.content.Context
import android.net.Uri

interface ImageHandlingInDatabaseRepository {

    suspend fun uploadImage(imageUri : Uri, context: Context):String?
    suspend fun getImageIdForFetching(
        documentId:String,
        collectionName:String,
        fieldName:String,
        back4appClassName : String
    ): String?
    suspend fun getUploadedImage(
        imageId: String,
        back4appClassName : String
    ): String?
}