package com.example.tetrainingandroid.data.storage

import android.content.SharedPreferences
import com.example.tetrainingandroid.data.response.RequestTokenResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestTokenResponseStorage @Inject constructor(
    prefs: SharedPreferences,
) : Storage<RequestTokenResponse>(prefs, REQUEST_TOKEN_KEY, EXPIRES_REQUEST_TOKEN_KEY) {
    companion object {
        const val REQUEST_TOKEN_KEY = "request_token"
        const val EXPIRES_REQUEST_TOKEN_KEY = "expires_request_token"
    }

    override fun get(defaultValue: RequestTokenResponse?): RequestTokenResponse? {
        if (prefs.contains(REQUEST_TOKEN_KEY)) {
            return RequestTokenResponse(
                requestToken = prefs.getString(REQUEST_TOKEN_KEY, ""),
                expiresAtSinceEpoch = prefs.getLong(EXPIRES_REQUEST_TOKEN_KEY, 0)
            )
        }
        return defaultValue
    }

    override fun save(value: RequestTokenResponse) {
        if (value.requestToken == null || value.requestToken.isEmpty() || value.expiresAt == null) return
        with(prefs.edit()) {
            putString(REQUEST_TOKEN_KEY, value.requestToken)
            putLong(EXPIRES_REQUEST_TOKEN_KEY, value.expiresAt.time)
            apply()
        }
    }
}