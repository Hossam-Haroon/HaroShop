package com.example.e_commerceapp.data.local.dataSources

import com.example.e_commerceapp.data.local.dao.CartDao
import com.example.e_commerceapp.data.local.data.CartDbEntity
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

class LocalCartDataSource @Inject constructor(
    private val cartDao: CartDao
) {
    suspend fun upsertAllCarts(carts:List<CartDbEntity>) = cartDao.upsertAllCarts(carts)
    fun getAllCarts() = cartDao.getAllCarts()
    suspend fun insertOrUpdateCart(cart:CartDbEntity) = cartDao.insertOrUpdateCart(cart)
    suspend fun getUnSyncedCarts() = cartDao.getUnSyncedCarts()
    suspend fun markCartAsSynced(productId:String) = cartDao.markCartAsSynced(productId)

}