package com.example.e_commerceapp.domain.usecases.paymentUseCases

import com.example.e_commerceapp.domain.repositories.PaymentRepository
import javax.inject.Inject

class CreateCustomerIdUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository
) {
    suspend operator fun invoke(email:String) = paymentRepository.createCustomer(email)
}