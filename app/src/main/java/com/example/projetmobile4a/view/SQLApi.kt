package com.example.projetmobile4a.view

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projetmobile4a.model.RestGroup
import com.example.projetmobile4a.model.RestUser


@Dao
interface SQLApi {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: RestUser)

    @Query("select * from RestUser where id = :id")
    fun getUser(id: Int): RestUser?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addGroup(group: RestGroup)

    @Query("select * from RestGroup where id = :id")
    fun getGroup(id: Int): RestGroup?
}