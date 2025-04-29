package com.example.projectauth.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) : SessionManagerInterface {

    private val prefs: SharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    override fun saveToken(token: String) {
        prefs.edit().putString("token", token).apply()
    }

    override fun getToken(): String? = prefs.getString("token", null)

    override fun clearToken() {
        prefs.edit().remove("token").apply()
        prefs.edit().remove("login_time").apply()
    }

    override fun saveLoginTime(timeMillis: Long) {
        prefs.edit().putLong("login_time", timeMillis).apply()
    }

    override fun getLoginTime(): Long {
        return prefs.getLong("login_time", 0L)
    }
}
