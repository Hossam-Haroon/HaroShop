package com.example.e_commerceapp.domain.usecases.productUseCases

import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.repositories.ProductRepository
import javax.inject.Inject

class GetProductDataUsingImageIdUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(imageId:String):Result<Product?>{
        return productRepository.getProductDataUsingImageId(imageId)
    }
}