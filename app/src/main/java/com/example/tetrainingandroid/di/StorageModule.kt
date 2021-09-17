package com.example.tetrainingandroid.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.tetrainingandroid.config.Config
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class MainKeyAlias

@Qualifier
annotation class SharePreferencesFileName

@InstallIn(SingletonComponent::class)
@Module
object StorageModule {
    @MainKeyAlias
    @Singleton
    @Provides
    fun providerMainKeyAlias(): String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)


    @SharePreferencesFileName
    @Singleton
    @Provides
    fun providerPrefsName(): String = Config.SHARED_PREFERENCES_NAME

    @Singleton
    @Provides
    fun providerSharePreferences(
        @SharePreferencesFileName fileName: String,
        @MainKeyAlias mainKeyAlias: String,
        @ApplicationContext applicationContext: Context
    ): SharedPreferences = EncryptedSharedPreferences.create(
        fileName,
        mainKeyAlias,
        applicationContext,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
    )
}