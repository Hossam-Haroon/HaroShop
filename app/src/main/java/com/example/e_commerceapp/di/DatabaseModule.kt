package com.example.e_commerceapp.di

import android.content.Context
import androidx.room.Room
import com.example.e_commerceapp.data.local.dao.CartDao
import com.example.e_commerceapp.data.local.dao.CategoryDao
import com.example.e_commerceapp.data.local.dao.DiscountDao
import com.example.e_commerceapp.data.local.dao.OrderDao
import com.example.e_commerceapp.data.local.dao.ProductDao
import com.example.e_commerceapp.data.local.dao.ReviewDao
import com.example.e_commerceapp.data.local.dao.UserDao
import com.example.e_commerceapp.data.local.dao.VoucherDao
import com.example.e_commerceapp.data.local.dao.WishListProductsDao
import com.example.e_commerceapp.data.local.database.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RoomDatabase {
        return Room.databaseBuilder(
            context,
            RoomDatabase::class.java,
            "room_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: RoomDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: RoomDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(database: RoomDatabase): CategoryDao {
        return database.categoryDao()
    }
    @Provides
    @Singleton
    fun provideReviewDao(database: RoomDatabase): ReviewDao {
        return database.reviewDao()
    }
    @Provides
    @Singleton
    fun provideCartDao(database: RoomDatabase): CartDao {
        return database.cartDao()
    }
    @Provides
    @Singleton
    fun provideOrderDao(database: RoomDatabase): OrderDao {
        return database.orderDao()
    }
    @Provides
    @Singleton
    fun provideDiscountDao(database: RoomDatabase): DiscountDao {
        return database.discountDao()
    }

    @Provides
    @Singleton
    fun provideVoucherDao(database: RoomDatabase):VoucherDao{
        return database.voucherDao()
    }

    @Provides
    @Singleton
    fun provideWishListDao(database: RoomDatabase):WishListProductsDao{
        return database.wishListDao()
    }
}
