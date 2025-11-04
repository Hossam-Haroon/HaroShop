package com.example.e_commerceapp.di.repositories

import android.content.Context
import com.example.e_commerceapp.data.remote.BackendApiService
import com.example.e_commerceapp.data.repositories.CartRepositoryImpl
import com.example.e_commerceapp.data.repositories.CategoryRepositoryImpl
import com.example.e_commerceapp.data.repositories.DiscountRepositoryImpl
import com.example.e_commerceapp.data.repositories.FirebaseAuthRepositoryImpl
import com.example.e_commerceapp.data.repositories.ImageHandlingInDatabaseRepositoryImpl
import com.example.e_commerceapp.data.repositories.OrderRepositoryImpl
import com.example.e_commerceapp.data.repositories.PaymentRepositoryImpl
import com.example.e_commerceapp.data.repositories.ProductRepositoryImpl
import com.example.e_commerceapp.data.repositories.ReviewRepositoryImpl
import com.example.e_commerceapp.data.repositories.SearchPreferencesRepositoryImpl
import com.example.e_commerceapp.data.repositories.UserRepositoryImpl
import com.example.e_commerceapp.data.repositories.VouchersRepositoryImpl
import com.example.e_commerceapp.domain.repositories.AuthenticationRepository
import com.example.e_commerceapp.domain.repositories.CartRepository
import com.example.e_commerceapp.domain.repositories.CategoryRepository
import com.example.e_commerceapp.domain.repositories.DiscountRepository
import com.example.e_commerceapp.domain.repositories.ImageHandlingInDatabaseRepository
import com.example.e_commerceapp.domain.repositories.OrderRepository
import com.example.e_commerceapp.domain.repositories.PaymentRepository
import com.example.e_commerceapp.domain.repositories.ProductRepository
import com.example.e_commerceapp.domain.repositories.ReviewRepository
import com.example.e_commerceapp.domain.repositories.SearchPreferencesRepository
import com.example.e_commerceapp.domain.repositories.UserRepository
import com.example.e_commerceapp.domain.repositories.VouchersRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    @Singleton
    fun getAuthRepoImpl(firebaseAuth: FirebaseAuth): AuthenticationRepository {
        return FirebaseAuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun getUserRepoImpl(firestore: FirebaseFirestore): UserRepository =
        UserRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun getImageHandlingRepoImpl(firestore: FirebaseFirestore): ImageHandlingInDatabaseRepository {
        return ImageHandlingInDatabaseRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun getProduct(firestore: FirebaseFirestore): ProductRepository {
        return ProductRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun getReviewRepoImpl(firestore: FirebaseFirestore): ReviewRepository {
        return ReviewRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun getCategoryRepoImpl(firestore: FirebaseFirestore):CategoryRepository{
        return CategoryRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun getCartRepoImpl(firestore: FirebaseFirestore): CartRepository {
        return CartRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun getDiscountRepoImpl(firestore: FirebaseFirestore): DiscountRepository{
        return DiscountRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun getSearchPreferencesRepositoryImpl(
        @ApplicationContext context: Context
    ): SearchPreferencesRepository = SearchPreferencesRepositoryImpl(context)

    @Provides
    @Singleton
    fun getVoucherRepoImpl(
        firestore: FirebaseFirestore
    ): VouchersRepository = VouchersRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun getPaymentRepoImpl(
        backendApi : BackendApiService
    ): PaymentRepository = PaymentRepositoryImpl(backendApi)

    @Provides
    @Singleton
    fun getOrderRepoImpl(
        firestore: FirebaseFirestore
    ): OrderRepository = OrderRepositoryImpl(firestore)
}