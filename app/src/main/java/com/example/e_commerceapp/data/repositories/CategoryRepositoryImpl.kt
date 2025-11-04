package com.example.e_commerceapp.data.repositories

import com.example.e_commerceapp.data.remote.data.CategoryEntity
import com.example.e_commerceapp.data.mappers.toDomain
import com.example.e_commerceapp.core.Utils.CATEGORIES
import com.example.e_commerceapp.domain.model.Category
import com.example.e_commerceapp.domain.repositories.CategoryRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : CategoryRepository {
    override fun getCategoriesList(): Flow<List<Category>> {
        return callbackFlow{
            val listener = firestore.collection(CATEGORIES)
                .orderBy("productCount", Query.Direction.DESCENDING)
                .addSnapshotListener { snapShot, error ->
                    if (error != null){
                        close(error)
                        return@addSnapshotListener
                    }
                    val products = snapShot?.toObjects(
                        CategoryEntity::class.java
                    )?.toDomain() ?: emptyList()
                    trySend(products)
                }
            awaitClose { listener.remove() }
        }
    }

    override fun getSampleCategories(): Flow<List<Category>> {
            return callbackFlow{
                val listener = firestore.collection(CATEGORIES)
                    .orderBy("productCount", Query.Direction.DESCENDING)
                    .limit(4)
                    .addSnapshotListener { snapShot, error ->
                        if (error != null){
                            close(error)
                            return@addSnapshotListener
                        }
                        val products = snapShot?.toObjects(
                            CategoryEntity::class.java
                        )?.toDomain() ?: emptyList()
                        trySend(products)
                    }
                awaitClose { listener.remove() }
            }
    }
}