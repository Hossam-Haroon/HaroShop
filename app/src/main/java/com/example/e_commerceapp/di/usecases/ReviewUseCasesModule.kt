package com.example.e_commerceapp.di.usecases

import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.ReviewRepository
import com.example.e_commerceapp.domain.repositories.UserRepository
import com.example.e_commerceapp.domain.usecases.reviewUseCase.CheckIfCurrentUserHasReviewForCurrentProductUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.CheckIfReviewsAreEmptyOrNotUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.CreateReviewAndUpdateUserReviewIdsUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.DeleteReviewAndUpdateUserReviewsIdUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.GetAllProductReviewsUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.GetHighestRateReviewSampleUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.UpdateUserReviewUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReviewUseCasesModule {
    @Provides
    @Singleton
    fun getCheckIfCurrentUserHasReview(
        reviewRepository: ReviewRepository,
        authenticationRepository: AuthenticationRepository
    ) = CheckIfCurrentUserHasReviewForCurrentProductUseCase(
        reviewRepository,
        authenticationRepository
    )

    @Provides
    @Singleton
    fun getCheckIfReviewsAreEmptyOrNot(reviewRepository: ReviewRepository) =
        CheckIfReviewsAreEmptyOrNotUseCase(reviewRepository)

    @Provides
    @Singleton
    fun createReviewAndUpdateUserReviews(
        reviewRepository: ReviewRepository,
        userRepository: UserRepository,
        authenticationRepository: AuthenticationRepository
    ) = CreateReviewAndUpdateUserReviewIdsUseCase(
        reviewRepository,
        userRepository,
        authenticationRepository
    )

    @Provides
    @Singleton
    fun deleteReviewAndUpdateUserReviewsId(
        authenticationRepository: AuthenticationRepository,
        reviewRepository: ReviewRepository,
        userRepository: UserRepository
    ) = DeleteReviewAndUpdateUserReviewsIdUseCase(
        authenticationRepository,
        userRepository,
        reviewRepository
    )

    @Provides
    @Singleton
    fun getAllProductReviews(
        reviewRepository: ReviewRepository
    ) = GetAllProductReviewsUseCase(reviewRepository)

    @Provides
    @Singleton
    fun getHighestRateReview(
        reviewRepository: ReviewRepository
    ) = GetHighestRateReviewSampleUseCase(reviewRepository)

    @Provides
    @Singleton
    fun getUpdateUserReview(reviewRepository: ReviewRepository) =
        UpdateUserReviewUseCase(reviewRepository)
}