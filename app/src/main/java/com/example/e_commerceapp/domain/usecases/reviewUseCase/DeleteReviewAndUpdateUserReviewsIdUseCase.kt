package com.example.e_commerceapp.domain.usecases.reviewUseCase

import com.example.e_commerceapp.core.Utils.USER_REVIEWS_ID
import com.example.e_commerceapp.domain.model.Review
import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.ReviewRepository
import com.example.e_commerceapp.domain.repositories.UserRepository
import com.google.firebase.firestore.FieldValue
import javax.inject.Inject

class DeleteReviewAndUpdateUserReviewsIdUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) {
    suspend operator fun invoke(review: Review, productId:String){
        val userId = authenticationRepository.getCurrentUser()?.uid
            ?: throw IllegalStateException("User must be logged in")
        reviewRepository.deleteReview(review,productId)
        userRepository.updateUser(
            userId, mapOf(
                USER_REVIEWS_ID to FieldValue.arrayRemove(review.reviewId)
            )
        )
    }
}