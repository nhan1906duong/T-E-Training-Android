package com.example.tetrainingandroid.deeplink

import com.example.tetrainingandroid.config.Config
import com.example.tetrainingandroid.data.storage.RequestTokenResponseStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizePermissionHelper @Inject constructor(val storage: RequestTokenResponseStorage) {
    companion object {
        const val authenticate: String = "https://www.themoviedb.org/authenticate"
    }

    fun getAuthorizeLink() = "${authenticate}/${storage.get()?.requestToken}"
}