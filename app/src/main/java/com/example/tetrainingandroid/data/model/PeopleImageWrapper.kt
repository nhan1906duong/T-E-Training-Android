package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json

data class PeopleImageWrapper(
    @Json(name = "profiles") val profiles: List<Image>?,
)