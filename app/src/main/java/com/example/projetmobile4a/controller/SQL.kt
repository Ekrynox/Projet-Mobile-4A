package com.example.projetmobile4a.controller

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.projetmobile4a.converter.IntListConverter
import com.example.projetmobile4a.model.*
import com.example.projetmobile4a.view.SQLApi


@Database(entities = [RestUser::class, RestGroup::class, RestMessageGroup::class, RestMessageUser::class], version = 7, exportSchema = false)
@TypeConverters(IntListConverter::class)
abstract class SQL : RoomDatabase() {
    companion object {
        @JvmStatic private var instance: SQL? = null

        @JvmStatic fun getInstance(context: Context): SQL {
            if (instance == null) {
                instance = Room.databaseBuilder<SQL>(context, SQL::class.java, "restDatabase").allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }

            return instance!!
        }
    }

    abstract fun sql() : SQLApi
}