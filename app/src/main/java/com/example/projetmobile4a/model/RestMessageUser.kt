package com.example.projetmobile4a.model

import androidx.room.Entity

@Entity(tableName = "RestMessageUser")
class RestMessageUser(message: RestMessage? = null) : RestMessage(message)