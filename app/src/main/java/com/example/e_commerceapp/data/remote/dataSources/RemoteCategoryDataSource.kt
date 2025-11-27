package com.example.e_commerceapp.data.remote.dataSources

import com.example.e_commerceapp.core.Utils.CATEGORIES
import com.example.e_commerceapp.data.remote.data.CategoryEntity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RemoteCategoryDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun getCategoriesStream(): Flow<List<CategoryEntity>> {
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
                    ) ?: emptyList()
                    trySend(products)
                }
            awaitClose { listener.remove() }
        }
    }
}