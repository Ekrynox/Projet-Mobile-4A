package com.example.projetmobile4a.model

import com.google.gson.annotations.SerializedName

class RestMessage {
    @SerializedName("id")
    var id: Number? = null

    @SerializedName("user")
    var user: Number? = null

    @SerializedName("user2")
    var user2: Number? = null

    @SerializedName("date")
    var date: String? = null

    @SerializedName("data")
    var data: RestMessageData? = null
}