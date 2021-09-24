package com.example.tetrainingandroid.di

import com.example.tetrainingandroid.BuildConfig
import com.example.tetrainingandroid.config.Config
import com.example.tetrainingandroid.data.adapter.DateAdapter
import com.example.tetrainingandroid.data.adapter.SearchableAdapter
import com.example.tetrainingandroid.data.interceptor.ApiAuthorizationInterceptor
import com.example.tetrainingandroid.data.interceptor.AuthenticationInterceptor
import com.example.tetrainingandroid.data.interceptor.ExtraDataInterceptor
import com.example.tetrainingandroid.data.model.Searchable
import com.example.tetrainingandroid.data.service.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton
    @Provides
    fun provideMoshi(dateAdapter: DateAdapter, searchableAdapter: SearchableAdapter): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Date::class.java, dateAdapter)
            .add(Searchable::class.java, searchableAdapter)
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

    @Singleton
    @Provides
    fun provideClient(
        apiInterceptor: ApiAuthorizationInterceptor,
        extraDataInterceptor: ExtraDataInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiInterceptor)
            .addInterceptor(extraDataInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(Config.READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(Config.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Config.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)

    @Singleton
    @Provides
    fun providePeopleService(retrofit: Retrofit): PeopleService =
        retrofit.create(PeopleService::class.java)

    @Singleton
    @Provides
    fun provideMovieDBService(retrofit: Retrofit): MovieDBOauth2Service =
        retrofit.create(MovieDBOauth2Service::class.java)

    @Singleton
    @Provides
    fun provideSearchService(retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)

    @Singleton
    @Provides
    fun provideUserService(
        retrofit: Retrofit,
        okHttpClient: OkHttpClient,
        authenticationInterceptor: AuthenticationInterceptor
    ): UserService {
        val newClient = okHttpClient.newBuilder().addInterceptor(authenticationInterceptor).build()
        val authenticationRetrofit = retrofit.newBuilder().client(newClient).build()
        return authenticationRetrofit.create(UserService::class.java)
    }
}