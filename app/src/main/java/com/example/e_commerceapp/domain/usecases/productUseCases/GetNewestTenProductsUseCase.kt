package com.example.e_commerceapp.domain.usecases.productUseCases

import com.example.e_commerceapp.domain.repositories.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewestTenProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(): Flow<Result<List<Pair<String,String>>>> {
       return productRepository.getNewestTenProducts()
    }
}