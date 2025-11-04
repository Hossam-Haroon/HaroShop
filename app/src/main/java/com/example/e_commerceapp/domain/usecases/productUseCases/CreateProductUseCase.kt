package com.example.e_commerceapp.domain.usecases.productUseCases

import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.repositories.ProductRepository
import javax.inject.Inject

class CreateProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(product:Product){
        productRepository.createProduct(product)
    }
}