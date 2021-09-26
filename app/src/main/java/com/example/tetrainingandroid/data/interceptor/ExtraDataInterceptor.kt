package com.example.tetrainingandroid.data.interceptor

import com.example.tetrainingandroid.data.storage.LanguageStorage
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExtraDataInterceptor @Inject constructor(
    private val languageStorage: LanguageStorage
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url
        val newUrl = originalUrl.newBuilder().apply {
            addQueryParameter("language", languageStorage.get("en"))
        }.build()
        val builder = original.newBuilder().url(newUrl)
        builder.addHeader("Content-type", "application/json;charset=utf-8")
        val request = builder.build()
        return chain.proceed(request)
    }
}