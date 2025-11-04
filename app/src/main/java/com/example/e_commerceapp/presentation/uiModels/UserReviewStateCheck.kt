package com.example.e_commerceapp.presentation.uiModels

import com.example.e_commerceapp.domain.model.Review

data class UserReviewStateCheck(
    val doesReviewExist : Boolean,
    val review : Review
)
