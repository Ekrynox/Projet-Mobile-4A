package com.example.projetmobile4a.model

class RestGroupOrUser {
    constructor(group: RestGroup) {
        this.group = group
    }

    constructor(user: RestUser) {
        this.user = user
    }

    var group: RestGroup? = null
    var user: RestUser? = null
}