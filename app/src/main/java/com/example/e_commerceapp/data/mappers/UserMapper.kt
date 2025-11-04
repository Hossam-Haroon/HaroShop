package com.example.e_commerceapp.data.mappers

import com.example.e_commerceapp.data.remote.data.UserEntity
import com.example.e_commerceapp.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        userId,
        email,
        userName,
        phoneNumber,
        favoriteProducts,
        accountRole,
        recentlyViewed,
        reviewsId,
        imageUrl,
        userAddress,
        stripeCustomerId
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        userId,
        userName,
        email,
        phoneNumber,
        USER,
        emptyList(),
        favoriteProducts,
        reviewsId,
        imageUrl,
        userAddress,
        stripeCustomerId
    )
}

const val USER = "user"