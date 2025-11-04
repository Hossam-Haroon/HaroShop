package com.example.e_commerceapp.di.usecases

import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.ProductRepository
import com.example.e_commerceapp.domain.repositories.UserRepository
import com.example.e_commerceapp.domain.usecases.productUseCases.CreateProductUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetAllFlashSaleProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetSampleFlashSaleProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetMostPopularProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetNewestTenProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetProductByCategoryUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetProductByIdUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetProductDataUsingImageIdUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetProductsWithSpecificDiscountUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetSearchedProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.GetStoryProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.JustForYouProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.ToggleFavouriteProductsUseCase
import com.example.e_commerceapp.domain.usecases.productUseCases.UpdateProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductUseCasesModule {
    @Provides
    @Singleton
    fun getCreateProductUseCase(productRepository: ProductRepository) =
        CreateProductUseCase(productRepository)

    @Provides
    @Singleton
    fun getFlashSaleProducts(
        productRepository: ProductRepository
    ) = GetSampleFlashSaleProductsUseCase(productRepository)

    @Provides
    @Singleton
    fun getMostPopularProducts(
        productRepository: ProductRepository
    ) = GetMostPopularProductsUseCase(productRepository)

    @Provides
    @Singleton
    fun getNewestTenProducts(
        productRepository: ProductRepository
    ) = GetNewestTenProductsUseCase(productRepository)

    @Provides
    @Singleton
    fun getProductByCategory(
        productRepository: ProductRepository
    ) = GetProductByCategoryUseCase(productRepository)

    @Provides
    @Singleton
    fun getProductById(
        productRepository: ProductRepository
    ) = GetProductByIdUseCase(productRepository)

    @Provides
    @Singleton
    fun getProductDataUsingImageId(
        productRepository: ProductRepository
    ) = GetProductDataUsingImageIdUseCase(productRepository)

    @Provides
    @Singleton
    fun getUpdatedProduct(productRepository: ProductRepository) =
        UpdateProductUseCase(productRepository)

    @Provides
    @Singleton
    fun getToggleProductLike(
        userRepository: UserRepository,
        productRepository: ProductRepository,
        authenticationRepository: AuthenticationRepository
    ) = ToggleFavouriteProductsUseCase(productRepository, userRepository, authenticationRepository)

    @Provides
    @Singleton
    fun getStoryProducts(
        productRepository: ProductRepository
    ) = GetStoryProductsUseCase(productRepository)

    @Provides
    @Singleton
    fun getJustForYouProducts(
        productRepository: ProductRepository
    ) = JustForYouProductsUseCase(productRepository)

    @Provides
    @Singleton
    fun getProductsWithSpecificDiscountUseCase(
        productRepository: ProductRepository
    ) = GetProductsWithSpecificDiscountUseCase(productRepository)

    @Provides
    @Singleton
    fun getAllFlashSaleProductsUseCase(
        productRepository: ProductRepository
    ) = GetAllFlashSaleProductsUseCase(productRepository)

    @Provides
    @Singleton
    fun getSearchedProducts(
        productRepository: ProductRepository
    ) = GetSearchedProductsUseCase(productRepository)
}