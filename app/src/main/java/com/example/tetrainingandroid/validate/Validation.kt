package com.example.tetrainingandroid.validate

import com.example.tetrainingandroid.config.Config
import com.example.tetrainingandroid.data.response.RequestTokenResponse
import com.example.tetrainingandroid.data.model.Session
import java.util.*

object Validation {
    fun isRequestTokenExpire(response: RequestTokenResponse?): Boolean {
        if (response == null || response.requestToken.isNullOrEmpty() || response.expiresAtSinceEpoch == null) return true
        if (Calendar.getInstance().time.time - response.expiresAtSinceEpoch > Config.REQUEST_TOKEN_EXPIRES_AT) {
            return true
        }
        return false
    }

    fun isSessionExpire(response: Session?): Boolean {
        if (response == null || response.sessionId.isNullOrEmpty() || response.expiresAtSinceEpoch == null) return true
        if (Calendar.getInstance().time.time - response.expiresAtSinceEpoch!! > Config.SESSION_EXPIRES_AT) {
            return true
        }
        return false
    }



    fun isValidAccount(username: String, password: String): Boolean {
        return username == "patricklagger" && password == "31121995"
    }
}