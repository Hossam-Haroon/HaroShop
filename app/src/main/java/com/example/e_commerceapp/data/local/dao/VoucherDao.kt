package com.example.e_commerceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.e_commerceapp.data.local.data.VoucherDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VoucherDao {

    @Upsert
    suspend fun upsertAllVouchers(vouchers:List<VoucherDbEntity>)

    @Query("SELECT * FROM vouchers")
    fun getAllVouchers():Flow<List<VoucherDbEntity>>
}