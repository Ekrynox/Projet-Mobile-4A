package com.example.projetmobile4a.model

import com.google.gson.annotations.SerializedName

class RestGroupsList : RestDefault() {
    @SerializedName("groups")
    var groups: List<RestGroup>? = null
}