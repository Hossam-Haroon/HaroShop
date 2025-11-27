package com.example.e_commerceapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.e_commerceapp.data.local.converters.Converters
import com.example.e_commerceapp.data.local.converters.EnumConverters
import com.example.e_commerceapp.data.local.converters.OrderConverters
import com.example.e_commerceapp.data.local.dao.CartDao
import com.example.e_commerceapp.data.local.dao.CategoryDao
import com.example.e_commerceapp.data.local.dao.DiscountDao
import com.example.e_commerceapp.data.local.dao.OrderDao
import com.example.e_commerceapp.data.local.dao.ProductDao
import com.example.e_commerceapp.data.local.dao.ReviewDao
import com.example.e_commerceapp.data.local.dao.UserDao
import com.example.e_commerceapp.data.local.dao.VoucherDao
import com.example.e_commerceapp.data.local.dao.WishListProductsDao
import com.example.e_commerceapp.data.local.data.CartDbEntity
import com.example.e_commerceapp.data.local.data.CategoryDbEntity
import com.example.e_commerceapp.data.local.data.DiscountDbEntity
import com.example.e_commerceapp.data.local.data.OrderDbEntity
import com.example.e_commerceapp.data.local.data.ProductDbEntity
import com.example.e_commerceapp.data.local.data.ReviewDbEntity
import com.example.e_commerceapp.data.local.data.UserDbEntity
import com.example.e_commerceapp.data.local.data.VoucherDbEntity
import com.example.e_commerceapp.data.local.data.WishListProductDbEntity

@Database(
    entities = [
        ProductDbEntity::class,
        CategoryDbEntity::class,
        OrderDbEntity::class,
        UserDbEntity::class,
        DiscountDbEntity::class,
        CartDbEntity::class,
        ReviewDbEntity::class,
        VoucherDbEntity::class,
        WishListProductDbEntity::class
    ],
    version = 2
)
@TypeConverters(
    Converters::class,
    OrderConverters::class,
    EnumConverters::class
)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun orderDao(): OrderDao
    abstract fun userDao(): UserDao
    abstract fun discountDao(): DiscountDao
    abstract fun cartDao(): CartDao
    abstract fun reviewDao(): ReviewDao
    abstract fun voucherDao(): VoucherDao
    abstract fun wishListDao(): WishListProductsDao
}