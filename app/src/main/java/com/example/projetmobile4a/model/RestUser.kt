package com.example.projetmobile4a.model

import com.google.gson.annotations.SerializedName

class RestUser : RestDefault() {
    @SerializedName("id")
    var id: Number? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("pseudo")
    var pseudo: String? = null
}