package com.example.e_commerceapp.data.local.dataSources

import com.example.e_commerceapp.data.local.dao.DiscountDao
import com.example.e_commerceapp.data.local.data.DiscountDbEntity
import javax.inject.Inject

class LocalDiscountDataSource @Inject constructor(
    private val discountDao: DiscountDao
) {
    suspend fun upsertDiscounts(discounts:List<DiscountDbEntity>) =
        discountDao.upsertAllDiscountValues(discounts)

    fun getAllDiscounts() = discountDao.getAllDiscountValues()

}