package com.example.tetrainingandroid.data.storage

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginStorage @Inject constructor(
    prefs: SharedPreferences,
) : Storage<Boolean>(prefs, LOGIN_KEY) {
    companion object {
        const val LOGIN_KEY: String = "login"
    }

    override fun get(defaultValue: Boolean?): Boolean? {
        if (prefs.contains(LOGIN_KEY)) {
            return prefs.getBoolean(LOGIN_KEY, false)
        }
        return defaultValue
    }

    override fun save(value: Boolean) {
        with(prefs.edit()) {
            putBoolean(LOGIN_KEY, value)
            apply()
        }
    }
}