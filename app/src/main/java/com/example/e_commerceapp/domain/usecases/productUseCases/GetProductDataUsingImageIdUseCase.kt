package com.example.e_commerceapp.domain.usecases.productUseCases

import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.repositories.ProductRepository
import javax.inject.Inject

class GetProductDataUsingImageIdUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

}