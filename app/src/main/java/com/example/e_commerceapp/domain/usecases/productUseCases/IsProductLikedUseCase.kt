package com.example.e_commerceapp.domain.usecases.productUseCases

import com.example.e_commerceapp.domain.repositories.ProductRepository
import javax.inject.Inject

class IsProductLikedUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(productId:String) = productRepository.isProductLiked(productId)
}