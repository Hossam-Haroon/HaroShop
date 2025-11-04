package com.example.e_commerceapp.domain.usecases.reviewUseCase

import com.example.e_commerceapp.domain.repositories.ReviewRepository
import javax.inject.Inject

class CheckIfReviewsAreEmptyOrNotUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    suspend operator fun invoke(productId: String) =
        reviewRepository.checkIfReviewsAreEmptyOrNot(productId)
}