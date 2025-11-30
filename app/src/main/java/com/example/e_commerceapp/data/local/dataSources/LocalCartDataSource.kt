package com.example.e_commerceapp.data.local.dataSources

import com.example.e_commerceapp.data.local.dao.CartDao
import com.example.e_commerceapp.data.local.data.CartDbEntity
import javax.inject.Inject

class LocalCartDataSource @Inject constructor(
    private val cartDao: CartDao
) {
    suspend fun upsertAllCarts(carts:List<CartDbEntity>) = cartDao.upsertAllCarts(carts)
    fun getAllCarts() = cartDao.getAllCarts()
    suspend fun insertOrUpdateCart(cart:CartDbEntity) = cartDao.insertOrUpdateCart(cart)
}