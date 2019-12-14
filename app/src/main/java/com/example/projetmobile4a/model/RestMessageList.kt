package com.example.projetmobile4a.model

import com.google.gson.annotations.SerializedName

class RestMessageList : RestDefault() {
    @SerializedName("messages")
    var messages: List<RestMessage>? = null
}