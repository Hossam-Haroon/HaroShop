package com.example.e_commerceapp.domain.repositories

import com.example.e_commerceapp.domain.model.Voucher
import kotlinx.coroutines.flow.Flow

interface VouchersRepository {
    fun getAllUserVouchers(userId:String): Flow<List<Voucher>>
}