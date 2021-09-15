package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json
import java.util.*

data class RequestTokenResponse(
    @Json(name = "success") val success: Boolean? = null,
    @Json(name = "expires_at") val expiresAt: Date? = null,
    @Json(name = "request_token") val requestToken: String? = null,
    val expiresAtSinceEpoch: Long? = null
)