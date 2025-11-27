package com.example.e_commerceapp.data.remote.dataSources

import com.example.e_commerceapp.core.Utils.CART
import com.example.e_commerceapp.core.Utils.CART_AMOUNT
import com.example.e_commerceapp.core.Utils.CHOSEN_AMOUNT
import com.example.e_commerceapp.core.Utils.USER
import com.example.e_commerceapp.data.local.data.CartDbEntity
import com.example.e_commerceapp.data.mappers.toDbEntity
import com.example.e_commerceapp.data.mappers.toDomain
import com.example.e_commerceapp.data.remote.data.CartEntity
import com.example.e_commerceapp.domain.model.Cart
import com.example.e_commerceapp.domain.model.HaroShopException
import com.example.e_commerceapp.domain.model.Product
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Transaction
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteCartDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun getAllCartItems(userId: String): Flow<List<CartEntity>> {
        return callbackFlow {
            val listener = firestore.collection(USER).document(userId).collection(CART)
                .addSnapshotListener { snapShot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val cartProducts = snapShot?.toObjects(
                        CartEntity::class.java
                    ) ?: emptyList()
                    trySend(cartProducts)
                }
            awaitClose { listener.remove() }
        }
    }

    fun deleteItemFromCart(
        cartProduct: Cart,
        userId: String
    ) {
        firestore.collection(USER).document(userId).collection(CART)
            .document(
                "${cartProduct.productId}_${cartProduct.color}_${cartProduct.size}"
            )
            .delete()
    }

    suspend fun updateCartQuantity(userId: String, cartProduct: Cart, quantity: Int) {
        firestore.collection(USER)
            .document(userId)
            .collection(CART)
            .document(
                "${cartProduct.productId}_${cartProduct.color}_${cartProduct.size}"
            )
            .update(CART_AMOUNT, quantity).await()
    }

    suspend fun deleteAllProductsInCart(userId: String): Result<Unit> {
        val cartCollection = firestore.collection(USER)
            .document(userId)
            .collection(CART)
        val cartSnapShot = cartCollection.get().await()
        if (cartSnapShot.isEmpty) {
            return Result.success(Unit)
        }
        val batch = firestore.batch()
        for (doc in cartSnapShot.documents) {
            batch.delete(doc.reference)
        }
        batch.commit().await()
        return Result.success(Unit)
    }
}