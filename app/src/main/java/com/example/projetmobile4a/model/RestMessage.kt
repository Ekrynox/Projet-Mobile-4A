package com.example.projetmobile4a.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "RestMessage")
class RestMessage {
    @PrimaryKey
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("user")
    var user: Int? = null

    @SerializedName("user2")
    var user2: Int? = null

    @SerializedName("group")
    var group: Int? = null

    @SerializedName("date")
    var date: String? = null

    @Embedded
    @SerializedName("data")
    var data: RestMessageData? = null
}