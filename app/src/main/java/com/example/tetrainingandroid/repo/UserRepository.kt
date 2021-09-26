package com.example.tetrainingandroid.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.data.model.User
import com.example.tetrainingandroid.data.request.FavoriteRequestParams
import com.example.tetrainingandroid.data.request.ReviewRequestParams
import com.example.tetrainingandroid.data.request.WatchListRequestParams
import com.example.tetrainingandroid.data.response.PageResponse
import com.example.tetrainingandroid.data.response.PostResponse
import com.example.tetrainingandroid.data.service.UserService
import com.example.tetrainingandroid.di.DispatchersIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val service: UserService,
    @DispatchersIO private val coroutineDispatcher: CoroutineDispatcher,
) {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    suspend fun getUser() {
        withContext(coroutineDispatcher) {
            val response = service.getUser()
            withContext(Dispatchers.Main) {
                _user.value = response
            }
        }
    }

    suspend fun addToWatchList(movieId: Int): PostResponse? {
        val accountId = _user.value?.id ?: return null
        return withContext(coroutineDispatcher) {
            service.changeAddToWatchList(
                accountId = accountId,
                body = WatchListRequestParams.addToWatchlist(movieId)
            )
        }
    }

    suspend fun removeFromWatchlist(movieId: Int): PostResponse? {
        val accountId = _user.value?.id ?: return null
        return withContext(coroutineDispatcher) {
            service.changeAddToWatchList(
                accountId = accountId,
                body = WatchListRequestParams.removeFromWatchlist(movieId)
            )
        }
    }

    suspend fun markFavorite(movieId: Int): PostResponse? {
        val accountId = _user.value?.id ?: return null
        return withContext(coroutineDispatcher) {
            service.changeFavorite(
                accountId = accountId,
                body = FavoriteRequestParams.markFavorite(movieId)
            )
        }
    }

    suspend fun removeFromFavorite(movieId: Int): PostResponse? {
        val accountId = _user.value?.id ?: return null
        return withContext(coroutineDispatcher) {
            service.changeFavorite(
                accountId = accountId,
                body = FavoriteRequestParams.removeFavorite(movieId)
            )
        }
    }

    suspend fun postReview(movieId: Int, rating: Float?, content: String?) =
        withContext(coroutineDispatcher) {
            service.postRating(
                movieId,
                ReviewRequestParams(
                    value = rating,
                    content = content,
                )
            )
        }

    suspend fun getFavorite(page: Int): PageResponse<Movie>? {
        val accountId = _user.value?.id ?: return null
        return withContext(coroutineDispatcher) {
            service.getFavorite(
                accountId = accountId,
                page = page
            )
        }
    }
    suspend fun getWatchlist(page: Int): PageResponse<Movie>? {
        val accountId = _user.value?.id ?: return null
        return withContext(coroutineDispatcher) {
            service.getWatchList(
                accountId = accountId,
                page = page
            )
        }
    }
}