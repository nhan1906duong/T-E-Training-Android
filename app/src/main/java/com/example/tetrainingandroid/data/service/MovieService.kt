package com.example.tetrainingandroid.data.service

import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.data.model.MovieVideoWrapper
import com.example.tetrainingandroid.data.response.PageResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("/3/movie/upcoming")
    suspend fun getUpcoming(@Query("page") page: Int = 1): PageResponse<Movie>

    @GET("/3/movie/popular")
    suspend fun getPopular(@Query("page") page: Int = 1): PageResponse<Movie>

    @GET("/3/movie/top_rated")
    suspend fun getTopRated(@Query("page") page: Int = 1): PageResponse<Movie>

    @GET("/3/movie/now_playing")
    suspend fun getNowPlaying(@Query("page") page: Int = 1): PageResponse<Movie>

    @GET("/3/trending/movie/{time_window}")
    suspend fun getTrending(@Path("time_window") timeWindow: String): PageResponse<Movie>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Int,
        @Query("append_to_response") appendToResponse: String? = null
    ): Movie

    @GET("/3/movie/{movie_id}/videos")
    suspend fun getTrailers(@Path("movie_id") movieId: Int): MovieVideoWrapper
}