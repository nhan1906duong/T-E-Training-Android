package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json

data class User(
    @Json(name = "id") val id: Int?,
    @Json(name = "username") val username: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "avatar") val avatar: UserAvatar?
)