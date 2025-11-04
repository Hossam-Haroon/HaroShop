package com.example.e_commerceapp.data.repositories

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.example.e_commerceapp.core.Utils.BACK4APP_PRODUCTS_CLASS
import com.example.e_commerceapp.core.Utils.BACK4APP_USER_CLASS
import com.example.e_commerceapp.domain.repositories.ImageHandlingInDatabaseRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageHandlingInDatabaseRepositoryImpl @Inject constructor(
    private val firestore : FirebaseFirestore
):ImageHandlingInDatabaseRepository {
    override suspend fun uploadImage(imageUri: Uri, context: Context): String? =
        withContext(Dispatchers.IO){
        try{
            val parseFile = parseImagesForUpload(imageUri, context)
            val mediaItem = ParseObject(BACK4APP_USER_CLASS)
            parseFile?.let { mediaItem.put("file",it) }
            mediaItem.put("type","image")
            mediaItem.save()
            return@withContext mediaItem.objectId
        }catch (e:Exception){
            e.printStackTrace()
            return@withContext null
        }
    }

    suspend fun getImageUrls(productIds: List<String>): Map<String, String> = withContext(Dispatchers.IO) {
        val query = ParseQuery.getQuery<ParseObject>(BACK4APP_PRODUCTS_CLASS)

        // This is the key: "whereContainedIn"
        // It gets all objects whose objectId is in your list
        query.whereContainedIn("objectId", productIds)

        val results = query.find() // This is ONE network call

        // Convert the results to a simple Map
        return@withContext results.associate { parseObject ->
            val id = parseObject.objectId
            val url = (parseObject.get("file") as? ParseFile)?.url ?: ""
            id to url
        }
    }


    override suspend fun getImageIdForFetching(
        documentId:String,
        collectionName:String,
        fieldName:String,
        back4appClassName : String
    ): String? {
        val user = firestore.collection(collectionName)
            .document(documentId)
            .get()
            .await()
        val currentUserPicture = user.getString(fieldName)
        return currentUserPicture?.let { getUploadedImage(it,back4appClassName) }
    }
    override suspend fun getUploadedImage(
        imageId: String,
        back4appClassName : String
        ): String? = withContext(Dispatchers.IO){
        try{
            val query = ParseQuery.getQuery<ParseObject>(back4appClassName)
            val result = query.get(imageId)
            val url = (result.get("file") as ParseFile).url
            return@withContext url
        }catch (e : Exception){
            e.printStackTrace()
            return@withContext null
        }
    }
    private fun getFileExtension(context: Context, uri : Uri?): String?{
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(uri?.let {
            context.contentResolver.getType(
                it
            )
        })
    }
    private fun parseImagesForUpload(imageUri: Uri, context: Context):ParseFile?{
        val inputStream = context.contentResolver.openInputStream(imageUri)
        val bytes = inputStream?.readBytes()
        val fileName = "IMAGE_DATA"+System.currentTimeMillis()+"."+getFileExtension(
            context,imageUri
        )
        val parseFile = ParseFile(fileName,bytes)
        if (bytes == null){
            return null
        }
        parseFile.save()
        return parseFile
    }
}