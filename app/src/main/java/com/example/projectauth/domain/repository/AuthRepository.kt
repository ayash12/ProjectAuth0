package com.example.projectauth.domain.repository

import android.app.Activity
import com.auth0.android.result.Credentials
import com.auth0.android.authentication.AuthenticationException

interface AuthRepository {
    fun login(activity: Activity, onResult: (Credentials?, AuthenticationException?) -> Unit)
    fun register(activity: Activity, onResult: (Credentials?, AuthenticationException?) -> Unit)
    fun logout()
}