package com.example.tetrainingandroid.data.interceptor

import com.example.tetrainingandroid.data.storage.SessionStorage
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExtraDataInterceptor @Inject constructor(
    private val sessionStorage: SessionStorage
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()
        builder.addHeader("Content-type", "application/json;charset=utf-8")
        val request = builder.build()
        return chain.proceed(request)
    }
}