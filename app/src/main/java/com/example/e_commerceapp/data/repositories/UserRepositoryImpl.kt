package com.example.e_commerceapp.data.repositories

import android.util.Log
import com.example.e_commerceapp.data.remote.data.UserEntity
import com.example.e_commerceapp.core.Utils.EMAIL
import com.example.e_commerceapp.core.Utils.USER
import com.example.e_commerceapp.data.mappers.toDomain
import com.example.e_commerceapp.data.mappers.toEntity
import com.example.e_commerceapp.core.Utils.FAVOURITE_PRODUCTS
import com.example.e_commerceapp.core.Utils.RECENTLY_VIEWED
import com.example.e_commerceapp.core.Utils.USER_ADDRESS
import com.example.e_commerceapp.core.Utils.USER_EMAIL
import com.example.e_commerceapp.core.Utils.USER_PHONE_NUMBER
import com.example.e_commerceapp.core.Utils.USER_REVIEWS_ID
import com.example.e_commerceapp.domain.model.HaroShopException
import com.example.e_commerceapp.domain.model.User
import com.example.e_commerceapp.domain.repositories.UserRepository
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserRepository {
    override suspend fun createUser(user: User) {
        try {
            val userEntity = user.toEntity()
            firestore.collection(USER)
                .document(user.userId)
                .set(userEntity)
                .await()
        } catch (e: Exception) {
            Log.e("UserRepository", "Error creating user", e)
            throw e
        }
    }

    override suspend fun getUserByEmail(email: String): User? {
        val snapshot = firestore.collection(USER)
            .whereEqualTo(EMAIL, email)
            .get()
            .await()
        val userEntity = snapshot.documents.firstOrNull()?.toObject(UserEntity::class.java)
        return userEntity?.toDomain()
    }

    override suspend fun getUserReviewIds(userId: String): List<String> {
        val docSnapshot = firestore.collection(USER).document(userId).get().await()
        val reviewIds = docSnapshot.get(USER_REVIEWS_ID) as? List<String> ?: emptyList()
        return reviewIds
    }

    override suspend fun updateUser(userId: String, data: Map<String, Any>) {
        firestore.collection(USER)
            .document(userId)
            .update(data)
            .await()
    }

    override suspend fun deleteUser(userId: String) {
        firestore.collection(USER)
            .document(userId)
            .delete()
            .await()

    }

    override suspend fun getUserById(userId: String): User? {
        return try {
            val snapshot = firestore.collection(USER)
                .document(userId)
                .get()
                .await()
            snapshot?.toObject(UserEntity::class.java)?.toDomain()
        } catch (e: FirebaseFirestoreException) {
            throw HaroShopException.UnableToFetchProduct("unable to fetch user data")
        }
    }

    override fun getRecentlyViewedProductIdsFromUserCollection(
        userId: String
    ): Flow<List<String>?> {
        return callbackFlow {
            val listener = firestore.collection(USER)
                .document(userId)
                .addSnapshotListener { snapShot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val recentlyViewedList = snapShot?.get(
                        RECENTLY_VIEWED
                    ) as? List<String>
                    trySend(recentlyViewedList)
                }
            awaitClose { listener.remove() }
        }
    }

    override suspend fun addLastViewedProductIdToRecentlyViewedFieldInUserDocument(
        productId: String,
        userId: String
    ) {
        val recentlyViewedProducts = checkRecentlyViewedListSize(productId, userId)
        updateUser(userId, mapOf(RECENTLY_VIEWED to recentlyViewedProducts))
    }

    override suspend fun deleteFavouriteProduct(productId: String, userId: String) {
        try {
            updateUser(userId, mapOf(FAVOURITE_PRODUCTS to FieldValue.arrayRemove(productId)))
        }catch (e: FirebaseFirestoreException) {
            throw HaroShopException.UnableToUpdateDocumentWithModel(
                "unable to delete productId from favourite products field"
            )
        }
    }

    override suspend fun addProductIdToFavouriteProductsInUserDocument(
        productId: String,
        userId: String
    ) {
        try {
            updateUser(userId, mapOf(FAVOURITE_PRODUCTS to FieldValue.arrayUnion(productId)))
        } catch (e: FirebaseFirestoreException) {
            throw HaroShopException.UnableToUpdateDocumentWithModel(
                "unable to add productId to favourite products field"
            )
        }
    }

    override fun getFavouriteProductsIds(userId: String): Flow<List<String>> {
        return callbackFlow {
            val listener = getDocumentRefOfTheUser(userId).addSnapshotListener { snapShot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val favouriteProductsIds =
                    snapShot?.get(FAVOURITE_PRODUCTS) as? List<String> ?: emptyList()
                trySend(favouriteProductsIds)
            }
            awaitClose { listener.remove() }
        }
    }

    override suspend fun getDocumentRefOfTheUser(userId: String): DocumentReference {
        return firestore.collection(USER).document(userId)
    }

    override fun isProductLiked(productId: String, userId: String): Flow<Boolean> {
        return callbackFlow {
            val listener = getDocumentRefOfTheUser(userId).addSnapshotListener { snapShot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val favouriteProducts = snapShot?.get(
                    FAVOURITE_PRODUCTS
                ) as? List<String> ?: emptyList()
                trySend(productId in favouriteProducts)
            }
            awaitClose { listener.remove() }
        }
    }

    override suspend fun removeProductIdFromFavouriteProducts(productId: String, userId: String) {
        try {
            updateUser(userId, mapOf(FAVOURITE_PRODUCTS to FieldValue.arrayRemove(productId)))
        } catch (e: FirebaseFirestoreException) {
            throw HaroShopException.UnableToUpdateDocumentWithModel(
                "unable to remove productId from favourite products field"
            )
        }
    }

    override fun getUserAddress(userId: String): Flow<String> {
        return callbackFlow {
            val listener = firestore.collection(USER)
                .document(userId)
                .addSnapshotListener { snapShot, error ->
                    if (error != null){
                        close(error)
                        return@addSnapshotListener
                    }
                    val userAddress = snapShot?.get(USER_ADDRESS) as? String ?: ""
                    trySend(userAddress)
                }
            awaitClose { listener.remove() }
        }
    }
    override fun getUserEmail(userId: String): Flow<String> {
        return callbackFlow {
            val listener = firestore.collection(USER)
                .document(userId)
                .addSnapshotListener { snapShot, error ->
                    if (error != null){
                        close(error)
                        return@addSnapshotListener
                    }
                    val userEmail = snapShot?.get(USER_EMAIL) as? String ?: ""
                    trySend(userEmail)
                }
            awaitClose { listener.remove() }
        }
    }
    override fun getUserPhoneNumber(userId: String): Flow<String> {
        return callbackFlow {
            val listener = firestore.collection(USER)
                .document(userId)
                .addSnapshotListener { snapShot, error ->
                    if (error != null){
                        close(error)
                        return@addSnapshotListener
                    }
                    val userPhoneNumber = snapShot?.get(USER_PHONE_NUMBER) as? String ?: ""
                    trySend(userPhoneNumber)
                }
            awaitClose { listener.remove() }
        }
    }

    private suspend fun checkRecentlyViewedListSize(
        productId: String,
        userId: String
    ): List<String> {
        val list = getUserById(userId)?.recentlyViewed ?: emptyList()
        val updatedList = if (list.size > 5) {
            (list.takeLast(5) - productId) + productId
        } else {
            (list - productId) + productId
        }
        return updatedList
    }
}