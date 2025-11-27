package com.example.e_commerceapp.data.remote.dataSources


import com.example.e_commerceapp.core.Utils.PRODUCT
import com.example.e_commerceapp.data.mappers.toEntity
import com.example.e_commerceapp.data.remote.data.ProductEntity
import com.example.e_commerceapp.domain.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteProductDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun getAllProductsStream(): Flow<List<ProductEntity>> {
        return callbackFlow {
            val listener = firestore.collection(PRODUCT)
                .addSnapshotListener { snapShot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val products = snapShot?.toObjects(
                        ProductEntity::class.java
                    ) ?: emptyList()
                    trySend(products)
                }
            awaitClose { listener.remove() }
        }
    }

    suspend fun createProduct(product: Product) {
        val documentId = firestore.collection(PRODUCT).document()
        val userEntity = product.copy(productId = documentId.id).toEntity()
        documentId.set(userEntity).await()
    }

    suspend fun updateProduct(productId: String, data: Map<String, Any>) {
        firestore.collection(PRODUCT)
            .document(productId)
            .update(data)
            .await()
    }
}