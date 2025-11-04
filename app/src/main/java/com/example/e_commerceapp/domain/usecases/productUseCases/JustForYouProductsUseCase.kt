package com.example.e_commerceapp.domain.usecases.productUseCases

import com.example.e_commerceapp.domain.repositories.ProductRepository
import javax.inject.Inject

class JustForYouProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke() = productRepository.justForYouProducts()
}