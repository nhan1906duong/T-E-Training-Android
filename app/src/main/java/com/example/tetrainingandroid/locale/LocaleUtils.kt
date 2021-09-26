package com.example.tetrainingandroid.locale

import android.content.Context
import android.content.res.Configuration
import com.example.tetrainingandroid.data.storage.LanguageStorage
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Inject

@EntryPoint
@InstallIn(SingletonComponent::class)
interface LocaleUtilsWrapper {
    val utils: LocaleUtils
}

class LocaleUtils @Inject constructor(
    private val storage: LanguageStorage
) {
    fun setupLocale(context: Context?): Context? {
        return updateLocaleConfiguration(context, storage.get() ?: "en")
    }

    fun switchLocale(context: Context?) {
        when(storage.get() ?: "en") {
            "vi" -> changeLocale(context, "en")
            "en" -> changeLocale(context, "vi")
            else -> {}
        }
    }

    private fun changeLocale(context: Context?, language: String): Context? {
        storage.save(language)
        return updateLocaleConfiguration(context, language)
    }

    private fun updateLocaleConfiguration(context: Context?, language: String): Context? {
        val locale = Locale(language)
        val resources = context?.resources
        val configuration = Configuration(resources?.configuration)
        configuration.setLocale(locale)
        return context?.createConfigurationContext(configuration)
    }
}