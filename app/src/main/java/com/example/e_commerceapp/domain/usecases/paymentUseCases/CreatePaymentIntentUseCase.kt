package com.example.e_commerceapp.domain.usecases.paymentUseCases

import com.example.e_commerceapp.domain.repositories.PaymentRepository
import javax.inject.Inject

class CreatePaymentIntentUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository
) {
    suspend operator fun invoke(
        amount: Int,
        customerId: String,
        paymentMethodId: String
    ) = paymentRepository.createPaymentIntent(amount, customerId, paymentMethodId)
}