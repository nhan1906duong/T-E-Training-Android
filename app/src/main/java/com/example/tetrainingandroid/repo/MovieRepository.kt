package com.example.tetrainingandroid.repo

import com.example.tetrainingandroid.data.service.MovieService
import com.example.tetrainingandroid.di.DispatchersIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieService: MovieService,
    @DispatchersIO private val coroutineDispatcher: CoroutineDispatcher,
) {
    suspend fun getDetail(movieId: Int) = withContext(coroutineDispatcher) {
        movieService.getMovie(
            movieId,
            appendToResponse = "videos,images,casts,reviews,similar"
        )
    }

    suspend fun getTrending() =
        withContext(coroutineDispatcher) { movieService.getTrending("week") }

    suspend fun getPopular() = withContext(coroutineDispatcher) { movieService.getPopular() }

    suspend fun getTopRated() = withContext(coroutineDispatcher) { movieService.getPopular() }

    suspend fun getNowPlaying() = withContext(coroutineDispatcher) { movieService.getNowPlaying() }

    suspend fun getUpComing() = withContext(coroutineDispatcher) { movieService.getUpcoming() }

    suspend fun getTrailers(movieId: Int) =
        withContext(coroutineDispatcher) { movieService.getTrailers(movieId) }
}