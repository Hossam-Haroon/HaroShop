package com.example.e_commerceapp.data.mappers

import com.example.e_commerceapp.data.local.data.ReviewDbEntity
import com.example.e_commerceapp.data.remote.data.ReviewEntity
import com.example.e_commerceapp.domain.model.Review

fun ReviewEntity.toDbEntity(
    productId: String
): ReviewDbEntity {
    return ReviewDbEntity(
        reviewId!!, userId, productId, userName, userRate, reviewText, userImage,
        isSynced = true,
        toBeDeleted = false
    )
}

fun ReviewDbEntity.toNetworkEntity():ReviewEntity{
    return ReviewEntity(
        reviewId, userId,userName, userRate, reviewText, userImage
    )
}

fun ReviewDbEntity.toDomain(): Review? {
    return reviewId?.let {
        Review(
            it, userId, userName, userRate, reviewText, userImage
    )
    }
}

fun List<ReviewDbEntity>.toDomain():List<Review> = this.map { it.toDomain()!! }