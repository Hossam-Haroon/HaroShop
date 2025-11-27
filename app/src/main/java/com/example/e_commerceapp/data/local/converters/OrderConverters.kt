package com.example.e_commerceapp.data.local.converters

import androidx.room.TypeConverter
import com.example.e_commerceapp.data.remote.data.OrderItemEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class OrderConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromOrderItemList(value: String): List<OrderItemEntity> {
        val listType = object : TypeToken<List<OrderItemEntity>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toOrderItemList(list: List<OrderItemEntity>): String {
        return gson.toJson(list)
    }
}