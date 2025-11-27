package com.example.e_commerceapp.data.local.converters

import androidx.room.TypeConverter
import com.example.e_commerceapp.domain.model.DiscountType
import com.example.e_commerceapp.domain.model.VoucherType
import com.google.gson.Gson

class EnumConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromVoucherType(voucherType:VoucherType):String{
        return voucherType.name
    }

    @TypeConverter
    fun toVoucherType(string:String):VoucherType{
        return VoucherType.valueOf(string)
    }

    @TypeConverter
    fun fromDiscountType(discountType: DiscountType):String{
        return discountType.name
    }

    @TypeConverter
    fun toDiscountType(string:String):DiscountType{
        return DiscountType.valueOf(string)
    }
}