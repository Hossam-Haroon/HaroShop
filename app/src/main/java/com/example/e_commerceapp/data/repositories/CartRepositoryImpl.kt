package com.example.e_commerceapp.data.repositories

import com.example.e_commerceapp.core.Utils.CART
import com.example.e_commerceapp.core.Utils.CART_AMOUNT
import com.example.e_commerceapp.core.Utils.CHOSEN_AMOUNT
import com.example.e_commerceapp.core.Utils.USER
import com.example.e_commerceapp.data.remote.data.CartEntity
import com.example.e_commerceapp.data.mappers.toDomain
import com.example.e_commerceapp.domain.model.Cart
import com.example.e_commerceapp.domain.model.HaroShopException
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.repositories.CartRepository
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

class CartRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : CartRepository {
    override fun getAllCartItems(userId: String): Flow<List<Cart>> {
        return callbackFlow {
            val listener = firestore.collection(USER).document(userId).collection(CART)
                .addSnapshotListener { snapShot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val cartProducts = snapShot?.toObjects(
                        CartEntity::class.java
                    )?.toDomain() ?: emptyList()
                    trySend(cartProducts)
                }
            awaitClose { listener.remove() }
        }
    }

    override suspend fun deleteItemFromCart(
        cartProduct: Cart,
        userId: String
    ) {
        try {
            firestore.collection(USER).document(userId).collection(CART)
                .document(
                    "${cartProduct.productId}_${cartProduct.color}_${cartProduct.size}"
                )
                .delete()
                .await()
        } catch (e: FirebaseFirestoreException) {
            throw HaroShopException.UnableToDeleteItem("unable to make a cart")
        }
    }

    override suspend fun updateCartQuantity(userId: String, cartProduct: Cart, quantity: Int) {
        try {
            firestore.collection(USER)
                .document(userId)
                .collection(CART)
                .document(
                    "${cartProduct.productId}_${cartProduct.color}_${cartProduct.size}"
                )
                .update(CART_AMOUNT, quantity)
        } catch (e: FirebaseFirestoreException) {
            throw HaroShopException.UnableToUpdateDocumentWithModel("unable to upgrade cart amount")
        }
    }

    override suspend fun deleteAllProductsInCart(userId: String): Result<Unit> {
        return try {
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
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertProductToCart(
        product: Product,
        productAmount: Int,
        productColor: Long,
        productSize: String,
        userId: String,
        category:String
    ) {
        val documentRef = firestore.collection(USER)
            .document(userId)
            .collection(CART)
            .document(
                "${product.productId}_${productColor}_$productSize"
            )
        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(documentRef)
            val cart = CartEntity(
                product.productId,
                productAmount,
                productColor,
                productSize,
                product.productImage,
                product.description,
                product.productPrice.toFloat(),
                category
            )
            checkIfCartAlreadyExistsOrNotToSetOrUpdate(
                snapshot,
                transaction,
                cart,
                documentRef,
                productAmount
            )
        }
    }

    private fun checkIfCartAlreadyExistsOrNotToSetOrUpdate(
        snapshot: DocumentSnapshot,
        transaction: Transaction,
        cart: CartEntity,
        documentRef: DocumentReference,
        productAmount: Int
    ) {
        if (snapshot.exists()) {
            val existingCart = snapshot.toObject(CartEntity::class.java)?.toDomain()
            if (existingCart != null) {
                checkIfCartSnapShotHasSameFieldsAsAddedOneOrNot(
                    cart,
                    existingCart,
                    transaction,
                    documentRef,
                    productAmount
                )
            }
        } else {
            transaction.set(documentRef, cart)
        }
    }

    private fun checkIfCartSnapShotHasSameFieldsAsAddedOneOrNot(
        cart: CartEntity,
        existingCart: Cart,
        transaction: Transaction,
        documentRef: DocumentReference,
        productAmount: Int
    ) {
        if (cart.toDomain() == existingCart) {
            throw HaroShopException.ProductAlreadyInCart("Product already exists in")
        } else {
            transaction.update(documentRef, mapOf(CHOSEN_AMOUNT to productAmount))
        }
    }
}