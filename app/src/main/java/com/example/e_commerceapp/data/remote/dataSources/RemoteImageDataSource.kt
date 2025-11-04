package com.example.e_commerceapp.data.remote.dataSources

import com.example.e_commerceapp.core.Utils.BACK4APP_PRODUCTS_CLASS
import com.example.e_commerceapp.data.remote.data.Back4AppInfo
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteImageDataSource {

    suspend fun getImageUrls(productIds: List<String>): Map<String, Back4AppInfo> = withContext(
        Dispatchers.IO) {
        val query = ParseQuery.getQuery<ParseObject>(BACK4APP_PRODUCTS_CLASS)
        query.whereContainedIn("objectId", productIds)
        val results = query.find() // This is ONE network call
        return@withContext results.associate { parseObject ->
            val id = parseObject.objectId
            val url = (parseObject.get("file") as? ParseFile)?.url ?: ""
            val createdAt: Long = parseObject.createdAt.time
            id to Back4AppInfo(url,createdAt)
        }
    }
}