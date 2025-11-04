package com.example.e_commerceapp.domain.model

abstract class HaroShopException(message: String? = null) : Exception(message) {
    class NetworkException(message: String? = null) : HaroShopException(message)
    class InvalidInputException(message: String? = null) : HaroShopException(message)
    class ValidationException(message: String? = null) : HaroShopException(message)
    class NotFoundException(message: String? = null) : HaroShopException(message)
    class UnableToLogInException(message: String? = null): HaroShopException(message)
    class UnableToSignUpException(message: String? = null): HaroShopException(message)
    class UnableToFetchProduct(message: String? = null): HaroShopException(message)
    class UnableToCreateDocumentForModel(message: String? = null): HaroShopException(message)
    class UnableToDeleteItem(message: String? = null): HaroShopException(message)
    class UnableToUpdateDocumentWithModel(message: String? = null): HaroShopException(message)
    class ProductAlreadyInCart(message: String? = null): HaroShopException(message)
}