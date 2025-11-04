package com.example.e_commerceapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.e_commerceapp.data.local.converters.ProductConverters
import com.example.e_commerceapp.data.local.dao.ProductDao
import com.example.e_commerceapp.data.local.data.ProductDbEntity

@Database(entities = [ProductDbEntity::class], version = 1)
@TypeConverters(ProductConverters::class)
abstract class RoomDatabase:RoomDatabase() {
    abstract fun productDao():ProductDao
}