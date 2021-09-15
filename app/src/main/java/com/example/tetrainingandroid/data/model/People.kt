package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json

data class People(
    @Json(name = "adult") val adult: Boolean?,
    @Json(name = "also_known_as") val alsoKnownAs: List<String>?,
    @Json(name = "biography") val biography: String?,
    @Json(name = "birthday") val birthday: String?,
    @Json(name = "deathday") val deathDay: String?,
    @Json(name = "gender") val gender: Int?,
    @Json(name = "homepage") val homePage: Int?,
    @Json(name = "id") val id: Int?,
    @Json(name = "images") val images: PeopleImageWrapper?,
    @Json(name = "imdb_id") val imdbId: String?,
    @Json(name = "known_for_department") val acting: String?,
    @Json(name = "movie_credits") val movieCredits: PeopleWrapper?,
    @Json(name = "name") val name: String?,
    @Json(name = "place_of_birth") val placeOfBirth: String?,
    @Json(name = "original_name") val originalName: String?,
    @Json(name = "popularity") val popularity: Double?,
    @Json(name = "profile_path") val profilePath: String?,
    @Json(name = "cast_id") val castId: Int?,
    @Json(name = "character") val character: String?,
    @Json(name = "creditId") val creditId: String?,
    @Json(name = "order") val order: Int?,
    @Json(name = "department") val department: String?,
    @Json(name = "job") val job: String?,
): Searchable