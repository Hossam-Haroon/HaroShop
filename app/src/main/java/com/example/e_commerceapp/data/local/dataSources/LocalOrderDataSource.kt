package com.example.e_commerceapp.data.local.dataSources

import com.example.e_commerceapp.data.local.dao.OrderDao
import com.example.e_commerceapp.data.local.data.OrderDbEntity
import javax.inject.Inject

class LocalOrderDataSource @Inject constructor(
    private val orderDao: OrderDao
){
    suspend fun upsertAllOrders(orders:List<OrderDbEntity>) = orderDao.upsertAllOrders(orders)
    fun getAllOrders() = orderDao.getAllOrders()
}