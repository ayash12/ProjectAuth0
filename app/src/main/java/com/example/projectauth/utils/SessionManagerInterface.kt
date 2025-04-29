package com.example.projectauth.utils

interface SessionManagerInterface {
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
    fun saveLoginTime(timeMillis: Long)
    fun getLoginTime(): Long
}