package com.example.tetrainingandroid.repo

import android.util.Log
import com.example.tetrainingandroid.data.service.MovieDBOauth2Service
import com.example.tetrainingandroid.di.DispatchersIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeRepository @Inject constructor(
    @DispatchersIO private val coroutineDispatcher: CoroutineDispatcher,
    private val service: MovieDBOauth2Service
) {
    suspend fun request() {
        withContext(coroutineDispatcher) {
            val deferred = async { service.requestToken() }
            val result = deferred.await()
            Log.d("MSG", "Get success $result")
        }
    }
}
