package com.example.tetrainingandroid.data.interceptor

import android.content.Context
import com.example.tetrainingandroid.R
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationInterceptor @Inject constructor(@ApplicationContext val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        //TODO check isLogin
        val builder = original.newBuilder()
        builder.addHeader("Authorization", "Bearer ${context.getString(R.string.api_key_v4)}")
        val request = builder.build()
        return chain.proceed(request)
    }
}