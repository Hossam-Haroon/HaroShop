package com.example.e_commerceapp.domain.usecases.reviewUseCase

import com.example.e_commerceapp.domain.repositories.ReviewRepository
import javax.inject.Inject

class UpdateUserReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    suspend operator fun invoke(reviewId:String,productId:String,userRate:Int,userText:String){
        reviewRepository.updateReview(reviewId, productId, userRate, userText)
    }
}