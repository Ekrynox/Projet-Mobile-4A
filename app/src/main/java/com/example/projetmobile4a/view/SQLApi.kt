package com.example.projetmobile4a.view

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projetmobile4a.model.*


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


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMessageUser(message: RestMessageUser)

    @Query("select * from RestMessageUser where (user = :userId and user2 = :user2Id) or (user2 = :userId and user = :user2Id) order by id")
    fun getMessagesByUser(userId: Int, user2Id: Int): List<RestMessageUser>?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMessageGroup(message: RestMessageGroup)

    @Query("select * from RestMessageGroup where `group` = :id order by id")
    fun getMessagesByGroup(id: Int): List<RestMessageGroup>?
}