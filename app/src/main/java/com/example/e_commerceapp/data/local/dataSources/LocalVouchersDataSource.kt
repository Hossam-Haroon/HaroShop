package com.example.e_commerceapp.data.local.dataSources

import com.example.e_commerceapp.data.local.dao.VoucherDao
import com.example.e_commerceapp.data.local.data.VoucherDbEntity
import javax.inject.Inject

class LocalVouchersDataSource @Inject constructor(
    private val voucherDao: VoucherDao
) {

    suspend fun upsertAllVouchers(vouchers: List<VoucherDbEntity>) =
        voucherDao.upsertAllVouchers(vouchers)

    fun getAllVouchers() = voucherDao.getAllVouchers()
}