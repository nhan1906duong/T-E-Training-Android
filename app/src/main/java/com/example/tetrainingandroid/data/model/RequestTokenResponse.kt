package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json
import java.util.*

data class RequestTokenResponse(
    @Json(name = "success") val success: Boolean?,
    @Json(name = "expires_at") val expiresAt: Date?,
    @Json(name = "request_token") val requestToken: String?,
)