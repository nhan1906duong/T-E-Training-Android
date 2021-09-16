package com.example.tetrainingandroid.data.storage

import android.content.SharedPreferences

abstract class Storage<T>(val prefs: SharedPreferences, private vararg val keys: String) {
    abstract fun get(defaultValue: T? = null): T?

    abstract fun save(value: T)

    fun remove() {
        with(prefs.edit()) {
            for (key in keys) {
                if (prefs.contains(key)) {
                    remove(key)
                }
            }
            apply()
        }
    }
}