package com.example.tetrainingandroid.data.interceptor

import android.content.Context
import com.example.tetrainingandroid.R
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExtraDataInterceptor @Inject constructor(): Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()
        builder.addHeader("Content-type", "application/json;charset=utf-8")
        val request = builder.build()
        return chain.proceed(request)
    }
}