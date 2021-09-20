package com.example.tetrainingandroid.data.storage

import android.content.SharedPreferences
import com.example.tetrainingandroid.data.model.Session
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionStorage @Inject constructor(
    prefs: SharedPreferences,
) : Storage<Session>(prefs, SESSION_ID_KEY, EXPIRES_SESSION_KEY) {
    companion object {
        const val SESSION_ID_KEY: String = "session_id"
        const val EXPIRES_SESSION_KEY: String = "expires_session_id"
    }
    override fun get(defaultValue: Session?): Session? {
        if (prefs.contains(SESSION_ID_KEY)) {
            return Session(
                sessionId = prefs.getString(SESSION_ID_KEY, ""),
                expiresAtSinceEpoch = prefs.getLong(EXPIRES_SESSION_KEY, 0)
            )
        }
        return defaultValue
    }

    override fun save(value: Session) {
        if (value.sessionId == null || value.sessionId.isEmpty()) return
        with(prefs.edit()) {
            putString(SESSION_ID_KEY, value.sessionId)
            putLong(EXPIRES_SESSION_KEY, System.currentTimeMillis())
            apply()
        }
    }
}