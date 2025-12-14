package com.example.e_commerceapp.data.repositories

import com.example.e_commerceapp.data.local.dataSources.LocalOrderDataSource
import com.example.e_commerceapp.data.mappers.toDomain
import com.example.e_commerceapp.data.remote.dataSources.RemoteAuthDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteOrderDataSource
import com.example.e_commerceapp.domain.model.Order
import com.example.e_commerceapp.domain.repositories.OrderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val remoteOrderDataSource: RemoteOrderDataSource,
    private val localOrderDataSource: LocalOrderDataSource,
    remoteAuthDataSource: RemoteAuthDataSource
) : OrderRepository {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    init {
        remoteAuthDataSource.getCurrentUser()?.uid?.let{
            syncBackgroundData(it)
        }
    }
    private fun syncBackgroundData(userId: String){
        scope.launch {
            remoteOrderDataSource.getAllOrdersForCurrentUser(userId).collect{orders ->
                val validOrders = orders.filterNotNull()
                localOrderDataSource.upsertAllOrders(validOrders)
            }
        }
    }
    override suspend fun createOrder(order: Order, userId: String): Result<Unit> {
        return remoteOrderDataSource.createOrder(order, userId)
    }

    override suspend fun deleteAllOrders(){
        localOrderDataSource.deleteAllOrders()
    }

    override fun getAllOrdersForCurrentUser(userId: String): Flow<List<Order>> {
        return localOrderDataSource.getAllOrders().map {
            it.toDomain()
        }
    }
}