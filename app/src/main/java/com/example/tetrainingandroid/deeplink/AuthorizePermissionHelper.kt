package com.example.tetrainingandroid.deeplink

import com.example.tetrainingandroid.config.Config

class AuthorizePermissionHelper {
    companion object {
        const val authenticate: String = "${Config.BASE_URL}/authenticate"
    }

    fun getAuthorizeLink(requestToken: String) = "${authenticate}/$requestToken"
}