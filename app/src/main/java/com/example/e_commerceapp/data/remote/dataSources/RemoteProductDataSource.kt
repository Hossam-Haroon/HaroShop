package com.example.e_commerceapp.data.remote.dataSources

import com.example.e_commerceapp.core.Utils.PRODUCT
import com.example.e_commerceapp.data.mappers.toEntity
import com.example.e_commerceapp.data.remote.data.ProductEntity
import com.example.e_commerceapp.domain.model.HaroShopException
import com.example.e_commerceapp.domain.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
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
        try {
            val documentId = firestore.collection(PRODUCT).document()
            val userEntity = product.copy(productId = documentId.id).toEntity()
            documentId.set(userEntity).await()
        } catch (e: FirebaseFirestoreException) {
            HaroShopException.UnableToCreateDocumentForModel(
                "unable to create product"
            )
        }
    }

    suspend fun updateProduct(productId: String, data: Map<String, Any>) {
        try {
            firestore.collection(PRODUCT)
                .document(productId)
                .update(data)
                .await()
        } catch (e: FirebaseFirestoreException) {
            HaroShopException.UnableToUpdateDocumentWithModel(
                "unable to update product with the entered data."
            )
        }
    }
}