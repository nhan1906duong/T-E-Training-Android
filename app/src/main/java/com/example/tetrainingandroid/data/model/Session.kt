package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json
import java.util.*

data class Session(
    @Json(name = "success") val success: Boolean? = null,
    @Json(name = "session_id") val sessionId: String? = null,
    val expiresAtSinceEpoch: Long? = null,
)