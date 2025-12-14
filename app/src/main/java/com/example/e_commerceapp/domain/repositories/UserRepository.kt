package com.example.e_commerceapp.domain.repositories

import com.example.e_commerceapp.domain.model.User
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(user: User)
    suspend fun getUserByEmail(email: String): User?
    suspend fun getUserReviewIds(userId: String): Flow<List<String>>
    suspend fun updateUser(userId: String, data: Map<String, Any>)
    suspend fun deleteUser(userId: String)
    fun getCurrentUser(): Flow<User?>
    fun getRecentlyViewedProductIdsFromUserCollection(userId: String): Flow<List<String>?>
    suspend fun addLastViewedProductIdToRecentlyViewedFieldInUserDocument(productId: String)
    suspend fun getDocumentRefOfTheUser(): DocumentReference?
    fun getUserAddress(userId: String):Flow<String>
    fun getUserEmail(userId:String):Flow<String>
    fun getUserPhoneNumber(userId:String):Flow<String>
}