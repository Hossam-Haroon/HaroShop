package com.example.e_commerceapp.domain.usecases.discountUseCases

import com.example.e_commerceapp.domain.repositories.DiscountRepository
import javax.inject.Inject

class GetAllDiscountValuesUseCase @Inject constructor(
    private val discountRepository: DiscountRepository
) {
    operator fun invoke() = discountRepository.getAllDiscountValues()
}