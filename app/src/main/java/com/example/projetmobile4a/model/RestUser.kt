package com.example.projetmobile4a.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "RestUser")
class RestUser(error: String? = null) : RestDefault(error) {
    @PrimaryKey
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("pseudo")
    var pseudo: String? = null
}