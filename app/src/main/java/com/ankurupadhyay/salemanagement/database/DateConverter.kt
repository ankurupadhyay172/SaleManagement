package com.ankurupadhyay.salemanagement.database

import androidx.room.TypeConverter
import com.ankurupadhyay.salemanagement.data.MyCart
import com.ankurupadhyay.salemanagement.data.ReplaceCart
import com.ankurupadhyay.salemanagement.data.Variants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception
import java.util.*

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value:Long?):Date?{
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?):Long?{
        return date?.time?.toLong()
    }

    @TypeConverter
    fun modelToString(value:List<MyCart>):String{
        val gson = Gson()
        val type = object : TypeToken<List<MyCart>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringToModel(value:String):List<MyCart>{
        val gson = Gson()
        val type = object : TypeToken<List<MyCart>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun modelReplaceToString(value:List<ReplaceCart>):String{
        val gson = Gson()
        val type = object : TypeToken<List<ReplaceCart>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringToReplaceModel(value:String):List<ReplaceCart>{
        val gson = Gson()
        val type = object : TypeToken<List<ReplaceCart>>() {}.type
        return gson.fromJson(value, type)
    }



    @TypeConverter
    fun modelToStringProduct(value:List<Variants>?):String?{
        try {
            val gson = Gson()
            val type = object : TypeToken<List<Variants>>() {}.type
            return gson.toJson(value, type)
        }catch (e:Exception){
            return null
        }

    }

    @TypeConverter
    fun stringToModelProduct(value:String?):List<Variants>?{
        try {
            val gson = Gson()
            val type = object : TypeToken<List<Variants>>() {}.type
            return gson.fromJson(value, type)
        }catch (e:Exception){
            return null
        }

    }



    @TypeConverter
    fun modelToStringVariant(value:Variants?):String?{
        return try {
            val gson = Gson()
            //val type = object : TypeToken<List<Variants>>() {}.type
            gson.toJson(value, Variants::class.java)
        }catch (e:Exception){
            null
        }

    }

    @TypeConverter
    fun stringToModelVariant(value:String?):Variants?{
        return try {
            val gson = Gson()
            //val type = object : TypeToken<List<Variants>>() {}.type
            gson.fromJson(value, Variants::class.java)
        }catch (e:Exception){
            null
        }

    }
}