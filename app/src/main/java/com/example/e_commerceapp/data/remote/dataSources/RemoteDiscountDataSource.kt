package com.example.e_commerceapp.data.remote.dataSources

import com.example.e_commerceapp.core.Utils.DISCOUNT_COLLECTION
import com.example.e_commerceapp.core.Utils.DISCOUNT_VALUE_IN_DISCOUNT
import com.example.e_commerceapp.data.local.data.DiscountDbEntity
import com.example.e_commerceapp.data.mappers.toDbEntity
import com.example.e_commerceapp.data.mappers.toDomain
import com.example.e_commerceapp.data.remote.data.DiscountEntity
import com.example.e_commerceapp.domain.model.Discount
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RemoteDiscountDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun getAllDiscountValues(): Flow<List<DiscountDbEntity>> {
        return callbackFlow {
            val listener = firestore.collection(DISCOUNT_COLLECTION)
                .orderBy(DISCOUNT_VALUE_IN_DISCOUNT, Query.Direction.ASCENDING)
                .addSnapshotListener { snapShot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val discountValues = snapShot?.toObjects(
                        DiscountEntity::class.java
                    )?.toDbEntity() ?: emptyList()
                    trySend(discountValues)
                }
            awaitClose { listener.remove() }
        }
    }
}