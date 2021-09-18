package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json

data class Movie(
    @Json(name = "adult") val adult: Boolean?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "budget") val budget: Int?,
    @Json(name = "casts") val casts: PeopleWrapper?,
    @Json(name = "genres") val genres: List<Genre>?,
    @Json(name = "genre_ids") val genreIds: List<Int>?,
    @Json(name = "homepage") val homePage: String?,
    @Json(name = "id") val id: Int?,
    @Json(name = "images") val images: MovieImageWrapper?,
    @Json(name = "imdb_id") val imdbId: String?,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "original_title") val originalTitle: String?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "popularity") val popularity: Float?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "production_companies") val productionCompanies: List<ProductionCompany>?,
    @Json(name = "production_countries") val productionCountries: List<ProductionCountry>?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "revenue") val revenue: Int?,
    @Json(name = "runtime") val runtime: Int?,
    @Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguage>?,
    @Json(name = "status") val status: String?, //Rumored?, Planned?, In Production?, Post Production?, Released?, Canceled
    @Json(name = "tagline") val tagline: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "video") val video: Boolean?,
    @Json(name = "videos") val videos: MovieVideoWrapper?,
    @Json(name = "vote_average") val voteAverage: Float?,
    @Json(name = "vote_count") val voteCount: Int?,
    @Json(name = "reviews") val reviewResponse: ReviewResponse?
): Searchable