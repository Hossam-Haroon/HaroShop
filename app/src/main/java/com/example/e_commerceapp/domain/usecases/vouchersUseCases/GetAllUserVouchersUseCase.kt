package com.example.e_commerceapp.domain.usecases.vouchersUseCases

import com.example.e_commerceapp.domain.model.Voucher
import com.example.e_commerceapp.domain.repositories.VouchersRepository
import com.example.e_commerceapp.domain.usecases.authUseCases.GetCurrentUserUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUserVouchersUseCase @Inject constructor(
    private val vouchersRepository: VouchersRepository
) {
    operator fun invoke(): Flow<List<Voucher>> {
        return vouchersRepository.getAllUserVouchers()
    }
}