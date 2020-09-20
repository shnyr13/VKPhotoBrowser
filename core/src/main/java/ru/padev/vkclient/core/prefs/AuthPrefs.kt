package ru.padev.vkclient.core.prefs

import android.content.SharedPreferences
import javax.inject.Inject

class AuthPrefs @Inject constructor(
    prefs: SharedPreferences
) : BaseSharedPrefs(prefs) {

    private val PREF_SESSION_TOKEN_KEY = "ApplicationPreference.SessionToken"

    fun storeToken(token: String) {
        prefs.edit().putString(PREF_SESSION_TOKEN_KEY, token).commit()
    }

    fun getToken(): String = getString(PREF_SESSION_TOKEN_KEY)
}