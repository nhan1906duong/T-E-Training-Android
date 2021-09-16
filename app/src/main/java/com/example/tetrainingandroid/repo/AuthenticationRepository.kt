package com.example.tetrainingandroid.repo

import com.example.tetrainingandroid.data.model.Session
import com.example.tetrainingandroid.data.request.SessionRequestParams
import com.example.tetrainingandroid.data.request.ValidateRequestTokenParams
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
class AuthenticationRepository @Inject constructor(
    private val sessionStorage: SessionStorage,
    private val requestTokenStorage: RequestTokenResponseStorage,
    @DispatchersIO private val coroutineDispatcher: CoroutineDispatcher,
    private val service: MovieDBOauth2Service
) {

    suspend fun getSession(): Session? {
        val cacheSession = sessionStorage.get()
        if (Validation.isSessionExpire(cacheSession)) {
            return createNewSession()
        }
        return cacheSession
    }

    private suspend fun createNewSession(): Session? {
        var newSession: Session? = null
        withContext(coroutineDispatcher) {
            var requestTokenResponse = requestTokenStorage.get()
            if (Validation.isRequestTokenExpire(requestTokenResponse)) {
                requestTokenResponse = (async { service.requestToken() }).await()
                requestTokenStorage.save(requestTokenResponse)
            }
            val requestToken = requestTokenResponse?.requestToken
            if (requestToken == null) {
                requestTokenStorage.remove()
            } else {
                val result = (async { service.validateRequestToken(ValidateRequestTokenParams(requestToken)) }).await()
                if (result.success == true) {
                    val session = (async { service.createSession(SessionRequestParams(requestToken)) }).await()
                    sessionStorage.save(session)
                    if (session.success == true) {
                        newSession = session
                    } else {
                        sessionStorage.remove()
                    }
                } else {
                    requestTokenStorage.remove()
                }
            }
        }
        return newSession
    }
}
