package com.example.tetrainingandroid.repo

import com.example.tetrainingandroid.config.Config
import com.example.tetrainingandroid.data.request.SessionRequestParams
import com.example.tetrainingandroid.data.service.MovieDBOauth2Service
import com.example.tetrainingandroid.data.storage.RequestTokenResponseStorage
import com.example.tetrainingandroid.data.storage.SessionStorage
import com.example.tetrainingandroid.di.DispatchersIO
import com.example.tetrainingandroid.validate.Validation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationFakeRepository @Inject constructor(
    private val sessionStorage: SessionStorage,
    @DispatchersIO private val coroutineDispatcher: CoroutineDispatcher,
    private val service: MovieDBOauth2Service
) {
    suspend fun getRequestToken() {
        val sessionCached = sessionStorage.get()
        if (Validation.isSessionExpire(sessionCached)) {
            withContext(coroutineDispatcher) {
                val deferred = async { service.createSession(SessionRequestParams(requestToken = Config.REQUEST_TOKEN)) }
                val result = deferred.await()
                sessionStorage.save(result)
            }
        }

    }
}
