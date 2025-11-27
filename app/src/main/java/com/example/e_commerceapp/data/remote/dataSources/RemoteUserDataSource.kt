package com.example.e_commerceapp.data.remote.dataSources

import com.example.e_commerceapp.core.Utils.EMAIL
import com.example.e_commerceapp.core.Utils.USER
import com.example.e_commerceapp.data.local.data.UserDbEntity
import com.example.e_commerceapp.data.mappers.toDbEntity
import com.example.e_commerceapp.data.mappers.toDomain
import com.example.e_commerceapp.data.mappers.toEntity
import com.example.e_commerceapp.data.remote.data.UserEntity
import com.example.e_commerceapp.domain.model.User
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    fun getCurrentUser(userId: String): Flow<UserEntity?> {
        return callbackFlow {
            val listener =
                firestore.collection(USER)
                    .document(userId)
                    .addSnapshotListener { snapShot, error ->
                        if (error != null) {
                            close(error)
                            return@addSnapshotListener
                        }
                        val currentUser = snapShot?.toObject(UserEntity::class.java)
                        trySend(currentUser)
                    }
            awaitClose { listener.remove() }
        }
    }

    suspend fun createUser(user: User) {
        val userEntity = user.toEntity()
        firestore.collection(USER)
            .document(user.userId)
            .set(userEntity)
            .await()
    }

    suspend fun updateUser(userId: String, data: Map<String, Any>) {
        firestore.collection(USER)
            .document(userId)
            .update(data)
            .await()
    }

    suspend fun deleteUser(userId: String) {
        firestore.collection(USER)
            .document(userId)
            .delete()
            .await()
    }

    suspend fun getUserByEmail(email: String): User? {
        val snapshot = firestore.collection(USER)
            .whereEqualTo(EMAIL, email)
            .get()
            .await()
        val userEntity = snapshot.documents.firstOrNull()?.toObject(UserEntity::class.java)
        return userEntity?.toDomain()
    }

    suspend fun getDocumentRefOfTheUser(userId: String): DocumentReference {
        return firestore.collection(USER).document(userId)
    }
}