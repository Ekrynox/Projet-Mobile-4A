package com.example.projetmobile4a.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "RestUser")
class RestUser : RestDefault() {
    @PrimaryKey
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("pseudo")
    var pseudo: String? = null
}