package com.example.tetrainingandroid.data.interceptor

import com.example.tetrainingandroid.data.storage.SessionStorage
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationInterceptor @Inject constructor(
    private val sessionStorage: SessionStorage
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val originalUrl = original.url
        val newUrl = originalUrl.newBuilder().apply {
            addQueryParameter("language", "en")
            val sessionId = sessionStorage.get()?.sessionId
            if (!sessionId.isNullOrEmpty()) {
                addQueryParameter("session_id", sessionId)
            }
        }.build()

        val builder = original.newBuilder().url(newUrl)
        val request = builder.build()
        return chain.proceed(request)
    }
}