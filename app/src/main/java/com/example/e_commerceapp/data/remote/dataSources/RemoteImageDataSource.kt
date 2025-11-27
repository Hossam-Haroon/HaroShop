package com.example.e_commerceapp.data.remote.dataSources

import com.example.e_commerceapp.core.Utils.BACK4APP_PRODUCTS_CLASS
import com.example.e_commerceapp.core.Utils.BACK4APP_USER_CLASS
import com.example.e_commerceapp.data.remote.data.Back4AppInfo
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteImageDataSource @Inject constructor() {
    suspend fun getImageUrls(imagesIds: List<String>): Map<String, Back4AppInfo> = withContext(
        Dispatchers.IO) {
        val query = ParseQuery.getQuery<ParseObject>(BACK4APP_PRODUCTS_CLASS)
        query.whereContainedIn("objectId", imagesIds)
        val results = query.find()
        return@withContext results.associate { parseObject ->
            val id = parseObject.objectId
            val url = (parseObject.get("file") as? ParseFile)?.url ?: ""
            val createdAt: Long = parseObject.createdAt.time
            id to Back4AppInfo(url,createdAt)
        }
    }

    suspend fun getUserImageUrls(usersImagesIds: List<String>): Map<String, String> {
        return withContext(Dispatchers.IO) {
            val query = ParseQuery.getQuery<ParseObject>(BACK4APP_USER_CLASS)
            query.whereContainedIn("objectId", usersImagesIds)
            val results = query.find()
            return@withContext results.associate { parseObject ->
                val id = parseObject.objectId
                val url = (parseObject.get("file") as? ParseFile)?.url ?: ""
                id to url
            }
        }
    }

    suspend fun getUploadedImage(
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
}