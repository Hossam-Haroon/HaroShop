package com.example.e_commerceapp.di.repositories

import android.content.Context
import androidx.work.WorkManager
import com.example.e_commerceapp.data.local.dataSources.LocalCartDataSource
import com.example.e_commerceapp.data.local.dataSources.LocalCategoryDataSource
import com.example.e_commerceapp.data.local.dataSources.LocalDiscountDataSource
import com.example.e_commerceapp.data.local.dataSources.LocalOrderDataSource
import com.example.e_commerceapp.data.local.dataSources.LocalProductDataSource
import com.example.e_commerceapp.data.local.dataSources.LocalReviewDataSource
import com.example.e_commerceapp.data.local.dataSources.LocalUserDataSource
import com.example.e_commerceapp.data.local.dataSources.LocalVouchersDataSource
import com.example.e_commerceapp.data.local.dataSources.LocalWishListProductsDataSource
import com.example.e_commerceapp.data.remote.BackendApiService
import com.example.e_commerceapp.data.remote.dataSources.RemoteAuthDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteCartDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteCategoryDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteDiscountDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteImageDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteOrderDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteProductDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteReviewDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteUserDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteVouchersDataSource
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
    fun getAuthRepoImpl(
        remoteAuthDataSource: RemoteAuthDataSource,
        localUserDataSource: LocalUserDataSource,
        localOrderDataSource: LocalOrderDataSource
    ): AuthenticationRepository {
        return FirebaseAuthRepositoryImpl(
            remoteAuthDataSource,
            localUserDataSource,
            localOrderDataSource
        )
    }

    @Provides
    @Singleton
    fun getUserRepoImpl(
        localUserDataSource: LocalUserDataSource,
        remoteUserDataSource: RemoteUserDataSource,
        authDataSource: RemoteAuthDataSource,
        remoteImageDataSource: RemoteImageDataSource
    ): UserRepository =
        UserRepositoryImpl(
            remoteUserDataSource,
            localUserDataSource,
            authDataSource,
            remoteImageDataSource
        )

    @Provides
    @Singleton
    fun getImageHandlingRepoImpl(firestore: FirebaseFirestore): ImageHandlingInDatabaseRepository {
        return ImageHandlingInDatabaseRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun getProduct(
        localProductDataSource: LocalProductDataSource,
        remoteProductDataSource: RemoteProductDataSource,
        remoteImageDataSource: RemoteImageDataSource,
        localWishListProductsDataSource: LocalWishListProductsDataSource,
        workManager: WorkManager
    ): ProductRepository {
        return ProductRepositoryImpl(
            localProductDataSource,
            remoteProductDataSource,
            remoteImageDataSource,
            localWishListProductsDataSource,
            workManager
        )
    }

    @Provides
    @Singleton
    fun getReviewRepoImpl(
        remoteReviewDataSource: RemoteReviewDataSource,
        localReviewDataSource: LocalReviewDataSource,
        workManager: WorkManager
    ): ReviewRepository {
        return ReviewRepositoryImpl(
            remoteReviewDataSource,
            localReviewDataSource,
            workManager
        )
    }

    @Provides
    @Singleton
    fun getCategoryRepoImpl(
        remoteCategoryDataSource: RemoteCategoryDataSource,
        localCategoryDataSource: LocalCategoryDataSource,
        remoteImageDataSource: RemoteImageDataSource
    ): CategoryRepository {
        return CategoryRepositoryImpl(
            remoteCategoryDataSource,
            localCategoryDataSource,
            remoteImageDataSource
        )
    }

    @Provides
    @Singleton
    fun getCartRepoImpl(
        remoteCartDataSource: RemoteCartDataSource,
        localCartDataSource: LocalCartDataSource,
        authDataSource: RemoteAuthDataSource,
        remoteImageDataSource: RemoteImageDataSource,
        workManager: WorkManager
    ): CartRepository {
        return CartRepositoryImpl(
            remoteCartDataSource,
            localCartDataSource,
            remoteImageDataSource,
            authDataSource,
            workManager
        )
    }

    @Provides
    @Singleton
    fun getDiscountRepoImpl(
        localDiscountDataSource: LocalDiscountDataSource,
        remoteDiscountDataSource: RemoteDiscountDataSource
    ): DiscountRepository {
        return DiscountRepositoryImpl(localDiscountDataSource, remoteDiscountDataSource)
    }

    @Provides
    @Singleton
    fun getSearchPreferencesRepositoryImpl(
        @ApplicationContext context: Context
    ): SearchPreferencesRepository = SearchPreferencesRepositoryImpl(context)

    @Provides
    @Singleton
    fun getVoucherRepoImpl(
        remoteVouchersDataSource: RemoteVouchersDataSource,
        localVouchersDataSource: LocalVouchersDataSource,
        authDataSource: RemoteAuthDataSource
    ): VouchersRepository = VouchersRepositoryImpl(
        remoteVouchersDataSource, localVouchersDataSource, authDataSource
    )

    @Provides
    @Singleton
    fun getPaymentRepoImpl(
        backendApi: BackendApiService
    ): PaymentRepository = PaymentRepositoryImpl(backendApi)

    @Provides
    @Singleton
    fun getOrderRepoImpl(
        remoteOrderDataSource: RemoteOrderDataSource,
        localOrderDataSource: LocalOrderDataSource,
        authDataSource: RemoteAuthDataSource
    ): OrderRepository = OrderRepositoryImpl(
        remoteOrderDataSource, localOrderDataSource, authDataSource
    )
}