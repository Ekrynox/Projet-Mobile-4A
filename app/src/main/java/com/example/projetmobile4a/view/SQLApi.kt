package com.example.projetmobile4a.view

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projetmobile4a.model.RestGroup
import com.example.projetmobile4a.model.RestMessage
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


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMessage(message: RestMessage)

    @Query("select * from RestMessage where (user = :userId and user2 = :user2Id) or (user2 = :userId and user = :user2Id) order by id")
    fun getMessagesByUser(userId: Int, user2Id: Int): List<RestMessage>?

    @Query("select * from RestMessage where `group` = :id order by id")
    fun getMessagesByGroup(id: Int): List<RestMessage>?
}