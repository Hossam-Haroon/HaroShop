package com.example.e_commerceapp.domain.usecases.reviewUseCase

import com.example.e_commerceapp.domain.model.Review
import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.ReviewRepository
import javax.inject.Inject

class CheckIfCurrentUserHasReviewForCurrentProductUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(productId:String):Pair<Boolean,Review?>{
        val userId = authenticationRepository.getCurrentUser()?.uid
            ?: throw IllegalStateException("User must be logged in")
        return reviewRepository.checkIfCurrentUserHasReviewForCurrentProduct(productId, userId)
    }
}