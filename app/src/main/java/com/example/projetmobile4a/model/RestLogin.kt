package com.example.projetmobile4a.model

import com.google.gson.annotations.SerializedName

class RestLogin {
    @SerializedName("error")
    var error: String? = null

    @SerializedName("id")
    var id: Number? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("pseudo")
    var pseudo: String? = null
}