package com.example.projetmobile4a.model

import androidx.room.Embedded
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

open class RestMessage(message: RestMessage? = null) {
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

    init {
        id = message?.id
        user = message?.user
        user2 = message?.user2
        group = message?.group
        date = message?.date
        data = message?.data
    }
}