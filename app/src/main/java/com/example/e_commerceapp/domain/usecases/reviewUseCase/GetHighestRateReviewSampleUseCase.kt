package com.example.e_commerceapp.domain.usecases.reviewUseCase

import com.example.e_commerceapp.domain.model.Review
import com.example.e_commerceapp.domain.repositories.ReviewRepository
import javax.inject.Inject

class GetHighestRateReviewSampleUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    suspend operator fun invoke(productId:String):Review?{
        return reviewRepository.getHighestRateReviewSample(productId)
    }
}