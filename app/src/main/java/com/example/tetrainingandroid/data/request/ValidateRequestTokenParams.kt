package com.example.tetrainingandroid.data.request

import com.squareup.moshi.Json

data class ValidateRequestTokenParams(
    @Json(name = "username") val username: String,
    @Json(name = "password") val password: String,
    @Json(name = "request_token") val requestToken: String,
) {
    constructor(token: String): this(
        username = "nhandn",
        password = "test1234",
        requestToken = token
    )
}