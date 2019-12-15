package com.example.projetmobile4a.model

import com.google.gson.annotations.SerializedName

class RestMessage {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("user")
    var user: Int? = null

    @SerializedName("date")
    var date: String? = null

    @SerializedName("data")
    var data: RestMessageData? = null
}