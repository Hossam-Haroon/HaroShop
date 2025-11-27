package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.core.Utils.BACK4APP_USER_CLASS
import com.example.e_commerceapp.domain.model.Review
import com.example.e_commerceapp.domain.model.User
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetUploadedImageUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.GetAllProductReviewsUseCase
import com.example.e_commerceapp.domain.usecases.reviewUseCase.SyncBackgroundDataForReviewsUseCase
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsScreenViewModel @Inject constructor(
    private val getAllProductReviewsUseCase: GetAllProductReviewsUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val syncBackgroundDataForReviewsUseCase: SyncBackgroundDataForReviewsUseCase,
    savedStateHandle: SavedStateHandle
):ViewModel(){
    val productId = savedStateHandle.get<String>("productId") ?: ""
    val productReviews : StateFlow<List<Review>> =
        getAllProductReviewsUseCase(productId)
            .catch { emit(emptyList())
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    val currentUser : StateFlow<User?> = flow {
        emit(getUserByIdUseCase())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = null
    )

    init {
        syncBackgroundDataForReviews(productId)
    }

    private fun syncBackgroundDataForReviews(productId: String){
        syncBackgroundDataForReviewsUseCase(productId)
    }

}