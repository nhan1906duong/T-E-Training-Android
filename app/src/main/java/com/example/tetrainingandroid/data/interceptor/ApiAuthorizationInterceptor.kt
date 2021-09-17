package com.example.tetrainingandroid.data.interceptor

import com.example.tetrainingandroid.config.Config
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiAuthorizationInterceptor @Inject constructor(): Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()
        builder.addHeader("Authorization", "Bearer ${Config.APPLICATION_TOKEN_v4}")
        val request = builder.build()
        return chain.proceed(request)
    }
}