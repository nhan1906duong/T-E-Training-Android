package com.example.tetrainingandroid.repo

import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.data.model.Youtube
import com.example.tetrainingandroid.data.request.ReviewRequestParams
import com.example.tetrainingandroid.data.response.PostResponse
import com.example.tetrainingandroid.data.service.MovieService
import com.example.tetrainingandroid.data.service.UserService
import com.example.tetrainingandroid.di.DispatchersIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieService: MovieService,
    private val userService: UserService,
    @DispatchersIO private val coroutineDispatcher: CoroutineDispatcher,
) {
    suspend fun getDetail(movieId: Int): Movie {
        val movie: Movie
        withContext(coroutineDispatcher) {
            movie = (async {
                movieService.getMovie(
                    movieId,
                    appendToResponse = "videos,images,casts,reviews,similar"
                )
            }).await()
        }
        return movie
    }

    suspend fun postReview(movieId: Int, rating: Float?, content: String?): PostResponse {
        val result: PostResponse
        withContext(coroutineDispatcher) {
            result = (async {
                userService.postRating(
                    movieId,
                    ReviewRequestParams(
                        value = rating,
                        content = content,
                    )
                )
            }).await()
        }
        return result
    }

    suspend fun getTrending(): List<Movie> {
        val result: List<Movie>
        withContext(coroutineDispatcher) {
            result = (async { movieService.getTrending("week") }).await().results ?: listOf()
        }
        return result
    }

    suspend fun getPopular(): List<Movie> {
        val result: List<Movie>
        withContext(coroutineDispatcher) {
            result = (async { movieService.getPopular() }).await().results ?: listOf()
        }
        return result
    }

    suspend fun getTopRated(): List<Movie> {
        val result: List<Movie>
        withContext(coroutineDispatcher) {
            result = (async { movieService.getTopRated() }).await().results ?: listOf()
        }
        return result
    }

    suspend fun getNowPlaying(): List<Movie> {
        val result: List<Movie>
        withContext(coroutineDispatcher) {
            result = (async { movieService.getNowPlaying() }).await().results ?: listOf()
        }
        return result
    }

    suspend fun getUpComing(): List<Movie> {
        val result: List<Movie>
        withContext(coroutineDispatcher) {
            result = (async { movieService.getUpcoming() }).await().results ?: listOf()
        }
        return result
    }

    suspend fun getTrailers(movieId: Int): List<Youtube> {
        val result: List<Youtube>
        withContext(coroutineDispatcher) {
            result = (async { movieService.getTrailers(movieId) }).await().results ?: listOf()
        }
        return result
    }
}