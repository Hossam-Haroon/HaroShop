package com.example.e_commerceapp.domain.usecases.reviewUseCase

import com.example.e_commerceapp.domain.repositories.ReviewRepository
import javax.inject.Inject

class GetAllProductReviewsUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
){
    operator fun invoke(productId:String) = reviewRepository.getAllProductReviews(productId)
}