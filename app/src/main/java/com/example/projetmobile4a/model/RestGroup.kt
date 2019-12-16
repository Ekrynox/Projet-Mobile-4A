package com.example.projetmobile4a.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "RestGroup")
class RestGroup : RestDefault() {
    @PrimaryKey
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("name")
    var name: String? = null

    var usersId: List<Int>? = null

    @Ignore
    @SerializedName("users")
    var users: List<RestUser>? = null
}