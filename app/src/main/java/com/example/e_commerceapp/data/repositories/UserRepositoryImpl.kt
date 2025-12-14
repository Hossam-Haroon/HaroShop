package com.example.e_commerceapp.data.repositories

import android.util.Log
import com.example.e_commerceapp.core.Utils.BACK4APP_USER_CLASS
import com.example.e_commerceapp.data.mappers.toDomain
import com.example.e_commerceapp.core.Utils.FAVOURITE_PRODUCTS
import com.example.e_commerceapp.core.Utils.RECENTLY_VIEWED
import com.example.e_commerceapp.data.local.data.UserDbEntity
import com.example.e_commerceapp.data.local.dataSources.LocalUserDataSource
import com.example.e_commerceapp.data.mappers.toDbEntity
import com.example.e_commerceapp.data.remote.dataSources.RemoteAuthDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteImageDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteUserDataSource
import com.example.e_commerceapp.domain.model.HaroShopException
import com.example.e_commerceapp.domain.model.User
import com.example.e_commerceapp.domain.repositories.UserRepository
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val localUserDataSource: LocalUserDataSource,
    private val authDataSource: RemoteAuthDataSource,
    private val remoteImageDataSource: RemoteImageDataSource,
) : UserRepository {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        scope.launch {
            authDataSource.observeAuthState().collect { firebaseUser ->
                if (firebaseUser != null) {
                    syncCurrentUser(firebaseUser.uid)
                } else {
                    localUserDataSource.deleteCurrentUser()
                }
            }
        }
    }

    private fun syncCurrentUser(userId: String) {
        scope.launch {
            remoteUserDataSource.getCurrentUser(userId).collect { userEntity ->
                if (userEntity != null) {
                    val imageId = userEntity.imageUrl ?: ""
                    val imageUrl = remoteImageDataSource.getUploadedImage(
                        imageId, BACK4APP_USER_CLASS
                    )
                    val userDbEntity = userEntity.copy(imageUrl = imageUrl).toDbEntity()
                    localUserDataSource.addCurrentUser(userDbEntity)
                }
            }
        }
    }

    fun refreshCurrentUser() {
        authDataSource.getCurrentUser()?.uid?.let { userId ->
            syncCurrentUser(userId)
        }
    }

    override suspend fun createUser(user: User) {
        return remoteUserDataSource.createUser(user)
    }

    override suspend fun getUserByEmail(email: String): User? {
        return remoteUserDataSource.getUserByEmail(email)
    }

    override suspend fun getUserReviewIds(userId: String): Flow<List<String>> {
        return localUserDataSource.getCurrentUser().map { user ->
            user?.reviewsId ?: emptyList()
        }
    }

    override suspend fun updateUser(userId: String, data: Map<String, Any>) {
        return remoteUserDataSource.updateUser(userId, data)
    }

    override suspend fun deleteUser(userId: String) {
        remoteUserDataSource.deleteUser(userId)
    }

    override fun getCurrentUser(): Flow<User?> {
        return localUserDataSource.getCurrentUser()
            .map {
                it?.toDomain()
            }
            .distinctUntilChanged()
    }

    override fun getRecentlyViewedProductIdsFromUserCollection(
        userId: String
    ): Flow<List<String>?> {
        return localUserDataSource.getCurrentUser().map { user ->
            user?.recentlyViewed
        }
    }

    override suspend fun addLastViewedProductIdToRecentlyViewedFieldInUserDocument(
        productId: String
    ) {
        val currentUser = localUserDataSource.getCurrentUser().first() ?: return
        val userId = currentUser.userId
        val recentlyViewedProducts = checkRecentlyViewedListSize(productId, currentUser)
        remoteUserDataSource.updateUser(
            userId,
            mapOf(RECENTLY_VIEWED to recentlyViewedProducts)
        )
        //val updatedUser = currentUser.copy(recentlyViewed = recentlyViewedProducts)
        //localUserDataSource.addCurrentUser(updatedUser)
    }

    override suspend fun getDocumentRefOfTheUser(): DocumentReference? {
        val userRef = authDataSource.getCurrentUser()?.uid?.let {
            remoteUserDataSource.getDocumentRefOfTheUser(it)
        }
        return userRef
    }

    override fun getUserAddress(userId: String): Flow<String> {
        return localUserDataSource.getCurrentUser().map { user ->
            user?.userAddress ?: ""
        }
    }

    override fun getUserEmail(userId: String): Flow<String> {
        return localUserDataSource.getCurrentUser().map { user ->
            user?.email ?: ""
        }
    }

    override fun getUserPhoneNumber(userId: String): Flow<String> {
        return localUserDataSource.getCurrentUser().map { user ->
            user?.phoneNumber ?: ""
        }
    }

    private fun checkRecentlyViewedListSize(
        productId: String,
        user: UserDbEntity
    ): List<String> {
        val list = user.recentlyViewed
        val updatedList = if (list.size > 5) {
            (list.takeLast(5) - productId) + productId
        } else {
            (list - productId) + productId
        }
        return updatedList
    }
}