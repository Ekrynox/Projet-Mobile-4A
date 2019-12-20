package com.example.projetmobile4a.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IntListConverter {
    @TypeConverter
    fun toText(users: List<Int>?) : String? {
        if (users == null) {
            return null
        }

        val gson = Gson()
        return gson.toJson(users)
    }

    @TypeConverter
    fun toList(users: String?) : List<Int>? {
        if (users == null) {
            return null
        }

        val gson = Gson()
        val type = object : TypeToken<List<Int>>(){}.type
        return gson.fromJson(users, type)
    }
}