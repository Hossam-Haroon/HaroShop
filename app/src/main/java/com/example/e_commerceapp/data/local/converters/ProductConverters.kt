package com.example.e_commerceapp.data.local.converters

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class ProductConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value,listType)
    }

    @TypeConverter
    fun toStringList(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromLongList(value: String): List<Long> {
        val listType = object : TypeToken<List<Long>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toLongList(list: List<Long>): String {
        return gson.toJson(list) }
}