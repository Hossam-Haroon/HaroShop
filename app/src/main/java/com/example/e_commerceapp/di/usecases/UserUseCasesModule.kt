package com.example.e_commerceapp.di.usecases

import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.UserRepository
import com.example.e_commerceapp.domain.usecases.userUseCases.AddLastViewedProductIdToRecentlyViewedFieldInUserDocumentUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.AddProductIdToFavouriteProductsInUserDocumentUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.CreateUserUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.DeleteFavouriteProductUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.DeleteUserUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetRecentlyViewedProductIdsFromUserCollectionUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserAddressUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserByEmailUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserByIdUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserEmailUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserPhoneNumberUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.IsProductLikedUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.RemoveProductIdFromFavouriteProductsUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.UpdateUserAddressUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserUseCasesModule {
    @Provides
    @Singleton
    fun getCreateUserUseCase(userRepository: UserRepository) = CreateUserUseCase(userRepository)

    @Provides
    @Singleton
    fun getUserByEmailUseCase(userRepository: UserRepository) =
        GetUserByEmailUseCase(userRepository)

    @Provides
    @Singleton
    fun getUserByIdUseCase(
        userRepository: UserRepository,
        authenticationRepository: AuthenticationRepository
    ) = GetUserByIdUseCase(userRepository, authenticationRepository)

    @Provides
    @Singleton
    fun getRecentlyViewedProductIds(
        userRepository: UserRepository,
        authenticationRepository: AuthenticationRepository
    ) = GetRecentlyViewedProductIdsFromUserCollectionUseCase(
        userRepository,
        authenticationRepository
    )

    @Provides
    @Singleton
    fun getAddLastViewedProductIdToRecentlyViewed(
        userRepository: UserRepository,
        authenticationRepository: AuthenticationRepository
    ) = AddLastViewedProductIdToRecentlyViewedFieldInUserDocumentUseCase(
        userRepository,
        authenticationRepository
    )

    @Provides
    @Singleton
    fun getIsProductLiked(
        userRepository: UserRepository,
        authenticationRepository: AuthenticationRepository
    ) = IsProductLikedUseCase(userRepository, authenticationRepository)

    @Provides
    @Singleton
    fun getAddProductIdToFavourites(
        userRepository: UserRepository,
        authenticationRepository: AuthenticationRepository
    ) = AddProductIdToFavouriteProductsInUserDocumentUseCase(
        userRepository,
        authenticationRepository
    )

    @Provides
    @Singleton
    fun getRemoveProductFromFavouriteProducts(
        userRepository: UserRepository,
        authenticationRepository: AuthenticationRepository
    ) = RemoveProductIdFromFavouriteProductsUseCase(userRepository, authenticationRepository)

    @Provides
    @Singleton
    fun getUpdateUserAddress(
        userRepository: UserRepository,
        authenticationRepository: AuthenticationRepository
    ) = UpdateUserAddressUseCase(userRepository, authenticationRepository)

    @Provides
    @Singleton
    fun getUserAddress(
        userRepository: UserRepository,
        authenticationRepository: AuthenticationRepository
    ) = GetUserAddressUseCase(userRepository, authenticationRepository)

    @Provides
    @Singleton
    fun getDeleteFavouriteProduct(
        userRepository: UserRepository,
        authenticationRepository: AuthenticationRepository
    ) = DeleteFavouriteProductUseCase(userRepository, authenticationRepository)

    @Provides
    @Singleton
    fun getDeleteUser(
        userRepository: UserRepository,
        authenticationRepository: AuthenticationRepository
    ) = DeleteUserUseCase(userRepository, authenticationRepository)

    @Provides
    @Singleton
    fun getUserEmail(
        userRepository: UserRepository,
        authenticationRepository: AuthenticationRepository
    ) = GetUserEmailUseCase(userRepository, authenticationRepository)

    @Provides
    @Singleton
    fun getUserPhoneNumber(
        userRepository: UserRepository,
        authenticationRepository: AuthenticationRepository
    ) = GetUserPhoneNumberUseCase(userRepository, authenticationRepository)
}