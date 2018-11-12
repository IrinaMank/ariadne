package com.zapir.ariadne.model.cache.entity

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson

class Converter {

    @TypeConverter
    fun listToJson(value: List<Int>?): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Int>? {

        val objects = Gson().fromJson(value, Array<Int>::class.java) as Array<Int>
        val list = objects.toList()
        return list
    }
}