package com.example.e_commerceapp.di.usecases

import com.example.e_commerceapp.domain.repositories.VouchersRepository
import com.example.e_commerceapp.domain.usecases.authUseCases.GetCurrentUserUseCase
import com.example.e_commerceapp.domain.usecases.vouchersUseCases.GetAllUserVouchersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VoucherUseCasesModule {
    @Provides
    @Singleton
    fun getAllVouchers(
        vouchersRepository: VouchersRepository,
        getCurrentUserUseCase: GetCurrentUserUseCase
    ) = GetAllUserVouchersUseCase(vouchersRepository, getCurrentUserUseCase)

}
