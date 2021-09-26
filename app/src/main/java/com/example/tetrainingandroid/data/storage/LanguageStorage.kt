package com.example.tetrainingandroid.data.storage

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageStorage @Inject constructor(
    prefs: SharedPreferences,
) : Storage<String>(prefs, LANGUAGE_KEY) {
    companion object {
        const val LANGUAGE_KEY: String = "language"
    }

    override fun get(defaultValue: String?): String? {
        if (prefs.contains(LANGUAGE_KEY)) {
            return prefs.getString(LANGUAGE_KEY, "en")
        }
        return defaultValue
    }

    override fun save(value: String) {
        with(prefs.edit()) {
            putString(LANGUAGE_KEY, value)
            apply()
        }
    }
}