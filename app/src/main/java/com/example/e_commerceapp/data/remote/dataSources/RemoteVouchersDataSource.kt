package com.example.e_commerceapp.data.remote.dataSources

import com.example.e_commerceapp.core.Utils.USER
import com.example.e_commerceapp.core.Utils.VOUCHER_COLLECTION
import com.example.e_commerceapp.data.local.data.VoucherDbEntity
import com.example.e_commerceapp.data.mappers.toDbEntity
import com.example.e_commerceapp.data.remote.data.VoucherEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RemoteVouchersDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun getAllUserVouchers(userId:String): Flow<List<VoucherDbEntity>> {
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
                    )?.toDbEntity() ?: emptyList()
                    trySend(vouchers)
                }
            awaitClose { listener.remove() }
        }
    }
}