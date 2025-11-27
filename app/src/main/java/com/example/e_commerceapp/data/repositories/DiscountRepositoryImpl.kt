package com.example.e_commerceapp.data.repositories

import com.example.e_commerceapp.core.Utils.DISCOUNT_COLLECTION
import com.example.e_commerceapp.core.Utils.DISCOUNT_VALUE_IN_DISCOUNT
import com.example.e_commerceapp.data.local.dataSources.LocalDiscountDataSource
import com.example.e_commerceapp.data.remote.data.DiscountEntity
import com.example.e_commerceapp.data.mappers.toDomain
import com.example.e_commerceapp.data.remote.dataSources.RemoteDiscountDataSource
import com.example.e_commerceapp.domain.model.Discount
import com.example.e_commerceapp.domain.repositories.DiscountRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class DiscountRepositoryImpl @Inject constructor(
    private val localDiscountDataSource: LocalDiscountDataSource,
    private val remoteDiscountDataSource: RemoteDiscountDataSource
) : DiscountRepository {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    init {
        syncBackgroundData()
    }
    private fun syncBackgroundData(){
        scope.launch {
            remoteDiscountDataSource.getAllDiscountValues().collect{discounts ->
                localDiscountDataSource.upsertDiscounts(discounts)
            }
        }
    }
    override fun getAllDiscountValues(): Flow<List<Discount>> {
        return localDiscountDataSource.getAllDiscounts().map {
            it.toDomain()
        }
    }
}