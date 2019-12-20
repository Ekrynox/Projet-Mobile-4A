package com.example.projetmobile4a.model

import androidx.room.Entity

@Entity(tableName = "RestMessageGroup")
class RestMessageGroup(message: RestMessage? = null) : RestMessage(message)