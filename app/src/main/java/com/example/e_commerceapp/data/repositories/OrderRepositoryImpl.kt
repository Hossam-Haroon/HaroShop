package com.example.e_commerceapp.data.repositories

import com.example.e_commerceapp.core.Utils.ORDER
import com.example.e_commerceapp.core.Utils.USER_ID
import com.example.e_commerceapp.data.remote.data.OrderEntity
import com.example.e_commerceapp.data.mappers.toDomain
import com.example.e_commerceapp.data.mappers.toEntity
import com.example.e_commerceapp.domain.model.Order
import com.example.e_commerceapp.domain.repositories.OrderRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : OrderRepository {
    override suspend fun createOrder(order: Order, userId: String): Result<Unit> {
        return try {
            val orderRef = firestore.collection(ORDER).document()
            val docId = orderRef.id
            val orderNumber = "ORD-${docId.takeLast(6).uppercase()}"
            val orderEntity = order.copy(
                orderId = docId,
                orderNumber = orderNumber,
                userId = userId
            ).toEntity()
            orderRef.set(orderEntity).await()
            Result.success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Result.failure(e)
        }
    }

    override fun getAllOrdersForCurrentUser(userId: String): Flow<List<Order>> {
        return callbackFlow {
            val listener = firestore.collection(ORDER)
                .whereEqualTo(USER_ID, userId)
                .addSnapshotListener { snapShot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val orders = snapShot?.toObjects(
                            OrderEntity::class.java
                    )?.toDomain() ?: emptyList()
                    trySend(orders)
                }
            awaitClose { listener.remove() }
        }
    }
}