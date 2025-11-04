package com.example.e_commerceapp.domain.usecases.vouchersUseCases

import com.example.e_commerceapp.domain.model.Voucher
import com.example.e_commerceapp.domain.repositories.VouchersRepository
import com.example.e_commerceapp.domain.usecases.authUseCases.GetCurrentUserUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUserVouchersUseCase @Inject constructor(
    private val vouchersRepository: VouchersRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) {
    operator fun invoke(): Flow<List<Voucher>> {
        val userId  = getCurrentUserUseCase()?.uid
            ?: throw IllegalStateException("User must be logged in")
        return vouchersRepository.getAllUserVouchers(userId)
    }
}