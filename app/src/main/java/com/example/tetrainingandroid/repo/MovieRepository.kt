package com.example.tetrainingandroid.repo

import android.util.Log
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.data.service.MovieService
import com.example.tetrainingandroid.di.DispatchersIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieService: MovieService,
    @DispatchersIO private val coroutineDispatcher: CoroutineDispatcher,
) {
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
}