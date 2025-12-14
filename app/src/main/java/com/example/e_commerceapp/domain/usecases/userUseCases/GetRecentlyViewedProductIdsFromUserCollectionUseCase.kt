package com.example.e_commerceapp.domain.usecases.userUseCases

import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRecentlyViewedProductIdsFromUserCollectionUseCase
@Inject constructor(
    private val userRepository: UserRepository,
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(): Flow<List<String>?> {
        return flow {
            val userId  = authenticationRepository.getCurrentUser()?.uid
            if (userId == null){
                emit(emptyList())
            }else{
                userRepository.getRecentlyViewedProductIdsFromUserCollection(userId).collect{
                    emit(it)
                }
            }
        }
    }
}