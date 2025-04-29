package com.example.projectauth.presentation.login

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.projectauth.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun login(activity: Activity, onResult: (token: String?) -> Unit) {
        authRepository.login(activity) { credentials, error ->
            if (credentials != null && error == null) {
                val token = credentials.accessToken
                Log.d("LoginViewModel", "Token didapatkan: $token")
                onResult(token)
            } else {
                Log.e("LoginViewModel", "Login gagal")
                onResult(null)
            }
        }
    }
}
