package com.example.e_commerceapp.di.usecases

import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.usecases.authUseCases.GetCurrentUserUseCase
import com.example.e_commerceapp.domain.usecases.authUseCases.LogInUseCase
import com.example.e_commerceapp.domain.usecases.authUseCases.LogOutUseCase
import com.example.e_commerceapp.domain.usecases.authUseCases.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {
    @Provides
    @Singleton
    fun getCurrentUserUseCase(
        authenticationRepository: AuthenticationRepository
    ): GetCurrentUserUseCase = GetCurrentUserUseCase(authenticationRepository)
    @Provides
    @Singleton
    fun getLogInUseCase(
        authenticationRepository: AuthenticationRepository
    ): LogInUseCase = LogInUseCase(authenticationRepository)
    @Provides
    @Singleton
    fun getLogOutUseCase(
        authenticationRepository : AuthenticationRepository
    ): LogOutUseCase = LogOutUseCase(authenticationRepository)
    @Provides
    @Singleton
    fun getSignUpUseCase(
        authenticationRepository: AuthenticationRepository
    ): SignUpUseCase = SignUpUseCase(authenticationRepository)
}