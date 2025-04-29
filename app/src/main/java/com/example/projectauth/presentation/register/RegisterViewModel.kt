package com.example.projectauth.presentation.register

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.projectauth.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun register(activity: Activity, onResult: (Boolean) -> Unit) {
        authRepository.register(activity) { credentials, error ->
            onResult(credentials != null && error == null)
        }
    }
}
