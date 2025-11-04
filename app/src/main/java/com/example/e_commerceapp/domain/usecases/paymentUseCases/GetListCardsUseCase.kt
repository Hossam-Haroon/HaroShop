package com.example.e_commerceapp.domain.usecases.paymentUseCases

import com.example.e_commerceapp.domain.repositories.PaymentRepository
import javax.inject.Inject

class GetListCardsUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository
) {
    suspend operator fun invoke(customerId:String) = paymentRepository.getListCards(customerId)
}