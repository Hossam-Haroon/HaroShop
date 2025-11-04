package com.example.e_commerceapp.domain.usecases.productUseCases

import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.repositories.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMostPopularProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(): Flow<Result<List<Product>>> {
        return productRepository.getMostPopularProducts()
    }
}