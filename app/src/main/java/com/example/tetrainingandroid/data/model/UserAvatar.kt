package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json

data class UserAvatar(
    @Json(name = "gravatar") val gravatar: Map<String, String?>?,
    @Json(name = "tmdb") val tmdb: Map<String, String?>?
) {
    fun getAvatarPath(): String? {
        if (tmdb?.containsKey("avatar_path") == true) {
            return tmdb["avatar_path"]
        }
        return null
    }
}