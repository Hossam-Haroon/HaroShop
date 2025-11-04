package com.example.e_commerceapp.domain.usecases.productUseCases

import com.example.e_commerceapp.domain.repositories.ProductRepository
import javax.inject.Inject

class GetSampleFlashSaleProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke() = productRepository.getFlashSaleProducts(6)
}