package com.example.e_commerceapp.domain.usecases.reviewUseCase

import com.example.e_commerceapp.domain.model.Review
import com.example.e_commerceapp.domain.repositories.ReviewRepository
import javax.inject.Inject

class CreateReviewAndUpdateUserReviewIdsUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    suspend operator fun invoke(review: Review, productId:String){
        reviewRepository.createReview(review,productId)
    }
}