package com.example.e_commerceapp.data.repositories

import com.example.e_commerceapp.core.Utils.USER
import com.example.e_commerceapp.core.Utils.VOUCHER_COLLECTION
import com.example.e_commerceapp.data.local.dataSources.LocalVouchersDataSource
import com.example.e_commerceapp.data.mappers.toDomain
import com.example.e_commerceapp.data.remote.data.VoucherEntity
import com.example.e_commerceapp.data.remote.dataSources.RemoteAuthDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteVouchersDataSource
import com.example.e_commerceapp.domain.model.Voucher
import com.example.e_commerceapp.domain.repositories.VouchersRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class VouchersRepositoryImpl @Inject constructor(
    private val remoteVouchersDataSource: RemoteVouchersDataSource,
    private val localVouchersDataSource: LocalVouchersDataSource,
    authDataSource: RemoteAuthDataSource
):VouchersRepository {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    init {
        authDataSource.getCurrentUser()?.uid?.let {
            syncBackgroundData(it)
        }
    }
    private fun syncBackgroundData(userId: String){
        scope.launch {
            remoteVouchersDataSource.getAllUserVouchers(userId).collect{vouchers ->
                localVouchersDataSource.upsertAllVouchers(vouchers)
            }
        }
    }

    override fun getAllUserVouchers(): Flow<List<Voucher>> {
        return localVouchersDataSource.getAllVouchers().map {
            it.toDomain()
        }
    }
}