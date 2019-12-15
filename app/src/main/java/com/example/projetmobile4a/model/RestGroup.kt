package com.example.projetmobile4a.model

import com.google.gson.annotations.SerializedName

class RestGroup : RestDefault() {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("users")
    var users: List<RestUser>? = null
}