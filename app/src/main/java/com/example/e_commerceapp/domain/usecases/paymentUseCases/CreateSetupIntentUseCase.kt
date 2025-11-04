package com.example.e_commerceapp.domain.usecases.paymentUseCases

import com.example.e_commerceapp.domain.repositories.PaymentRepository
import javax.inject.Inject

class CreateSetupIntentUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository
){
    suspend operator fun invoke(customerId:String) = paymentRepository.createSetupIntent(customerId)
}