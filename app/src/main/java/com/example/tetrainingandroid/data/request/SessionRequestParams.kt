package com.example.tetrainingandroid.data.request

import com.squareup.moshi.Json

data class SessionRequestParams(
    @Json(name = "request_token") val requestToken: String
)