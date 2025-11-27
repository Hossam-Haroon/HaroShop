package com.example.e_commerceapp.domain.usecases.reviewUseCase

import com.example.e_commerceapp.domain.model.Review
import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.ReviewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckIfCurrentUserHasReviewForCurrentProductUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(productId:String): Flow<Review?> {
        val userId = authenticationRepository.getCurrentUser()?.uid
            ?: throw IllegalStateException("User must be logged in")
        return reviewRepository.checkIfCurrentUserHasReviewForCurrentProduct(productId, userId)
    }
}