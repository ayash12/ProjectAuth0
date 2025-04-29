package com.example.projectauth.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectauth.domain.repository.AuthRepository
import com.example.projectauth.utils.SessionManager
import com.example.projectauth.utils.SessionManagerInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManagerInterface
) : ViewModel() {

    private val _secondsLeft = MutableLiveData(600)
    val secondsLeft: LiveData<Int> = _secondsLeft

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (true) {
                val loginTime = sessionManager.getLoginTime()
                val currentTime = System.currentTimeMillis()
                val elapsedSeconds = (currentTime - loginTime) / 1000
                val remainingSeconds = 600 - elapsedSeconds.toInt()

                if (remainingSeconds <= 0) {
                    _secondsLeft.postValue(0)
                    logout()
                    break
                } else {
                    _secondsLeft.postValue(remainingSeconds)
                }

                delay(1000)
            }
        }
    }

    fun logout() {
        sessionManager.clearToken()
        authRepository.logout()
    }
}

