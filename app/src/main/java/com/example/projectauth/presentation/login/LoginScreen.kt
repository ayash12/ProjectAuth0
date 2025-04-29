package com.example.projectauth.presentation.login

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.projectauth.utils.SessionManager

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val activity = context as Activity
    val sessionManager = SessionManager(context)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                viewModel.login(activity) { token ->
                    if (token != null) {
                        sessionManager.saveToken(token) // Simpan token dari Auth0
                        sessionManager.saveLoginTime(System.currentTimeMillis()) // Simpan waktu login
                        Log.d("LoginScreen", "Token berhasil disimpan: $token")
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        Log.e("LoginScreen", "Login gagal, token null")
                    }
                }
            }) {
                Text("Login with Auth0")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { navController.navigate("register") }) {
                Text("Go to Register")
            }
        }
    }
}
