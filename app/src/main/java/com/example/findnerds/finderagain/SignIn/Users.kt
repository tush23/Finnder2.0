package com.example.findnerds.finnder_2.ui.SignIn

class Users {
    var username: String? = null
    var email: String? = null
    var photUrl: String? = null
    var uid: String? = null

    constructor()

    constructor(user: String) {
        this.username = user
    }

    constructor(user: String, email: String, photUrl: String, uid: String) {
        this.username = user
        this.email = email
        this.photUrl = photUrl
        this.uid = uid
    }
}
