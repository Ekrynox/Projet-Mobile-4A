package com.example.projetmobile4a.model

import com.google.gson.annotations.SerializedName

class RestUsersList : RestDefault() {
    @SerializedName("users")
    var users: List<RestUser>? = null
}