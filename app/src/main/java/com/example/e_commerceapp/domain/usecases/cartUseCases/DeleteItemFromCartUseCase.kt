package com.example.e_commerceapp.domain.usecases.cartUseCases

import com.example.e_commerceapp.domain.model.Cart
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.CartRepository
import javax.inject.Inject

class DeleteItemFromCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(cartProduct: Cart) = cartRepository.deleteItemFromCart(cartProduct)

}