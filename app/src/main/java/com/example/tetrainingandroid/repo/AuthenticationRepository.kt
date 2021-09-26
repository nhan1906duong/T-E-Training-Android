package com.example.tetrainingandroid.repo

import androidx.lifecycle.MediatorLiveData
import com.example.tetrainingandroid.data.request.SessionRequestParams
import com.example.tetrainingandroid.data.request.ValidateRequestTokenParams
import com.example.tetrainingandroid.data.service.MovieDBOauth2Service
import com.example.tetrainingandroid.data.storage.LoginStorage
import com.example.tetrainingandroid.data.storage.RequestTokenResponseStorage
import com.example.tetrainingandroid.data.storage.SessionStorage
import com.example.tetrainingandroid.di.DispatchersIO
import com.example.tetrainingandroid.ui.splash.LoginState
import com.example.tetrainingandroid.validate.Validation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationRepository @Inject constructor(
    private val loginStorage: LoginStorage,
    private val requestTokenStorage: RequestTokenResponseStorage,
    private val sessionStorage: SessionStorage,
    @DispatchersIO private val coroutineDispatcher: CoroutineDispatcher,
    private val service: MovieDBOauth2Service
) {
    val loginState = MediatorLiveData<LoginState>()

    suspend fun checkLogin() {
        loginState.value = LoginState.Initialize
        val cacheSession = sessionStorage.get()
        val isLogin = loginStorage.get()
        if (!Validation.isSessionExpire(cacheSession)) {
            if (isLogin == true) {
                loginState.value = LoginState.Login
            } else {
                loginState.value = LoginState.ApiAuthorization
            }
        } else {
            cacheSession?.let { sessionStorage.remove() }
            createNewSession()
        }
    }

    private suspend fun createNewSession() {
        withContext(coroutineDispatcher) {
            var requestTokenResponse = requestTokenStorage.get()
            if (Validation.isRequestTokenExpire(requestTokenResponse)) {
                requestTokenStorage.remove()
                requestTokenResponse = (async { service.requestToken() }).await()
            }
            val requestToken = requestTokenResponse?.requestToken
            if (requestToken == null) {
                throw Exception("Request token is empty")
            } else {
                requestTokenStorage.save(requestTokenResponse!!)
                val result = (async { service.validateRequestToken(ValidateRequestTokenParams(requestToken)) }).await()
                if (result.success == true) {
                    val session = (async { service.createSession(SessionRequestParams(requestToken)) }).await()
                    if (session.success == true && !session.sessionId.isNullOrEmpty()) {
                        sessionStorage.save(session)
                        withContext(Dispatchers.Main) {
                            loginState.value = LoginState.ApiAuthorization
                        }
                    } else {
                        throw Exception("Session id is empty")
                    }
                } else {
                    throw Exception("Request token's Permission has denied")
                }
            }
        }
    }
}
