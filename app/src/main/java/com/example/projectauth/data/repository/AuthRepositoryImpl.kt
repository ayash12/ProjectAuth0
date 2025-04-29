package com.example.projectauth.data.repository

import android.app.Activity
import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.example.projectauth.domain.repository.AuthRepository
import com.example.projectauth.utils.SessionManagerInterface
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth0: Auth0,
    private val context: Context,
    private val sessionManager: SessionManagerInterface
) : AuthRepository {

    override fun login(activity: Activity, onResult: (Credentials?, AuthenticationException?) -> Unit) {
        WebAuthProvider.login(auth0)
            .withScheme("demo")
            .start(activity, object : com.auth0.android.callback.Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    onResult(null, error)
                }

                override fun onSuccess(result: Credentials) {
                    sessionManager.saveToken(result.accessToken ?: "")
                    sessionManager.saveLoginTime(System.currentTimeMillis()) // ⬅️ Ini penting
                    onResult(result, null)
                }
            })
    }

    override fun register(activity: Activity, onResult: (Credentials?, AuthenticationException?) -> Unit) {
        WebAuthProvider.login(auth0)
            .withScheme("demo")
            .withParameters(mapOf("screen_hint" to "signup")) // Buka halaman register langsung
            .start(activity, object : com.auth0.android.callback.Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    onResult(null, error)
                }

                override fun onSuccess(result: Credentials) {
                    sessionManager.saveToken(result.accessToken)
                    onResult(result, null)
                }
            })
    }

    override fun logout() {
        sessionManager.clearToken()
    }
}
