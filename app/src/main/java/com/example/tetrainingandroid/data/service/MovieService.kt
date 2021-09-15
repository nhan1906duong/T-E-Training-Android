package com.example.tetrainingandroid.data.service

import com.example.tetrainingandroid.data.model.Movie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("/movie/upcoming")
    suspend fun getUpcoming(page: Int = 1): List<Movie>

    @GET("/movie/popular")
    suspend fun getPopular(page: Int = 1): List<Movie>

    @GET("/movie/top_rated")
    suspend fun getTopRated(page: Int = 1): List<Movie>

    @GET("/movie/now_playing")
    suspend fun getNowPlaying(page: Int = 1): List<Movie>

    @GET("/trending/tv/{time_window}")
    suspend fun getTrending(@Path("time_window") timeWindow: String): List<Movie>

    @GET("/movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Int,
        @Query("append_to_response") appendToResponse: String? = null
    ): Movie
}