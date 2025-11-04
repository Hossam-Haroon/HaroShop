package com.example.e_commerceapp.domain.usecases.reviewUseCase

import com.example.e_commerceapp.core.Utils.USER_REVIEWS_ID
import com.example.e_commerceapp.domain.model.Review
import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.ReviewRepository
import com.example.e_commerceapp.domain.repositories.UserRepository
import com.google.firebase.firestore.FieldValue
import javax.inject.Inject

class CreateReviewAndUpdateUserReviewIdsUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val userRepository: UserRepository,
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(review: Review, productId:String){
        val userId = authenticationRepository.getCurrentUser()?.uid
            ?: throw IllegalStateException("User must be logged in")
        val docId = reviewRepository.createReview(review,productId)
        userRepository.updateUser(
            userId, mapOf(
                USER_REVIEWS_ID to FieldValue.arrayUnion(docId)
            )
        )
    }
}