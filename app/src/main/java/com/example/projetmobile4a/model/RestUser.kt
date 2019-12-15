package com.example.projetmobile4a.model

import com.google.gson.annotations.SerializedName

class RestUser : RestDefault() {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("pseudo")
    var pseudo: String? = null
}