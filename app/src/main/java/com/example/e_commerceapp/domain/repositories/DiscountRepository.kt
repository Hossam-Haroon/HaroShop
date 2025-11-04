package com.example.e_commerceapp.domain.repositories

import com.example.e_commerceapp.domain.model.Discount
import kotlinx.coroutines.flow.Flow

interface DiscountRepository {

    fun getAllDiscountValues(): Flow<List<Discount>>
}