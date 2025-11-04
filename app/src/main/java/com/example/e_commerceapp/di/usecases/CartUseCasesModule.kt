package com.example.e_commerceapp.di.usecases

import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.CartRepository
import com.example.e_commerceapp.domain.usecases.authUseCases.GetCurrentUserUseCase
import com.example.e_commerceapp.domain.usecases.cartUseCases.DeleteAllProductsInCartUseCase
import com.example.e_commerceapp.domain.usecases.cartUseCases.DeleteItemFromCartUseCase
import com.example.e_commerceapp.domain.usecases.cartUseCases.GetAllCartItemsUseCase
import com.example.e_commerceapp.domain.usecases.cartUseCases.InsertProductToCartUseCase
import com.example.e_commerceapp.domain.usecases.cartUseCases.UpdateCartAmountUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartUseCasesModule {

    @Provides
    @Singleton
    fun getAllCartItems(
        cartRepository: CartRepository,
        authenticationRepository: AuthenticationRepository
    ) = GetAllCartItemsUseCase(cartRepository,authenticationRepository)

    @Provides
    @Singleton
    fun getInsertProductToCart(
        cartRepository: CartRepository,
        authenticationRepository: AuthenticationRepository
    ) = InsertProductToCartUseCase(cartRepository,authenticationRepository)

    @Provides
    @Singleton
    fun getDeleteProductFromCart(
        cartRepository: CartRepository,
        authenticationRepository: AuthenticationRepository
    ) = DeleteItemFromCartUseCase(cartRepository,authenticationRepository)

    @Provides
    @Singleton
    fun getUpdateCartAmount(
        cartRepository: CartRepository,
        authenticationRepository: AuthenticationRepository
    ) = UpdateCartAmountUseCase(cartRepository, authenticationRepository)

    @Provides
    @Singleton
    fun clearCart(
        cartRepository: CartRepository,
        getCurrentUserUseCase: GetCurrentUserUseCase
    ) = DeleteAllProductsInCartUseCase(cartRepository, getCurrentUserUseCase)
}