package com.example.e_commerceapp.data.mappers

import com.example.e_commerceapp.data.remote.data.ReviewEntity
import com.example.e_commerceapp.domain.model.Review

fun ReviewEntity.toDomain(): Review {
    return Review(
        reviewId!!, userId, userName, userRate, reviewText, userImage
    )
}
fun Review.toEntity(): ReviewEntity {
    return ReviewEntity(
        reviewId, userId, userName, userRate, reviewText, userImage
    )
}