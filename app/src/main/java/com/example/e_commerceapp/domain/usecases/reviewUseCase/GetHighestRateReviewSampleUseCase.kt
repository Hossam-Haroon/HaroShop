package com.example.e_commerceapp.domain.usecases.reviewUseCase

import com.example.e_commerceapp.domain.model.Review
import com.example.e_commerceapp.domain.repositories.ReviewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHighestRateReviewSampleUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
     operator fun invoke(productId:String):Flow<Review?>{
        return reviewRepository.getHighestRateReviewSample(productId)
    }
}