package com.example.tetrainingandroid.data.interceptor

import com.example.tetrainingandroid.config.Config
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        //TODO check isLogin
        val builder = original.newBuilder()
        builder.addHeader("Authorization", "Bearer ${Config.APPLICATION_TOKEN_v4}")
        val request = builder.build()
        return chain.proceed(request)
    }
}