package com.example.e_commerceapp.data.repositories

import com.example.e_commerceapp.core.Utils.USER
import com.example.e_commerceapp.core.Utils.VOUCHER_COLLECTION
import com.example.e_commerceapp.data.mappers.toDomain
import com.example.e_commerceapp.data.remote.data.VoucherEntity
import com.example.e_commerceapp.domain.model.Voucher
import com.example.e_commerceapp.domain.repositories.VouchersRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class VouchersRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
):VouchersRepository {
    override fun getAllUserVouchers(userId:String): Flow<List<Voucher>> {
        return callbackFlow {
            val listener = firestore.collection(USER)
                .document(userId)
                .collection(VOUCHER_COLLECTION)
                .addSnapshotListener { snapShot, error ->
                    if (error != null){
                        close(error)
                        return@addSnapshotListener
                    }
                    val vouchers = snapShot?.toObjects(
                        VoucherEntity::class.java
                    )?.toDomain() ?: emptyList()
                    trySend(vouchers)
                }
            awaitClose { listener.remove() }
        }
    }

}