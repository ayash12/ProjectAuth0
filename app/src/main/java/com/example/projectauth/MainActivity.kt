package com.example.projectauth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projectauth.presentation.home.HomeScreen
import com.example.projectauth.presentation.login.LoginScreen
import com.example.projectauth.presentation.register.RegisterScreen
import com.example.projectauth.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthApp()
        }
    }
}

@Composable
fun AuthApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }

    val token = sessionManager.getToken()
    val loginTime = sessionManager.getLoginTime()
    val currentTime = System.currentTimeMillis()
    val isSessionValid = token != null && (currentTime - loginTime) <= (10 * 60 * 1000) // 10 menit

    Surface(color = MaterialTheme.colorScheme.background) {
        NavigationComponent(navController, isSessionValid)
    }
}

@Composable
fun NavigationComponent(navController: NavHostController, isLoggedIn: Boolean) {
    val startDestination = if (isLoggedIn) "home" else "login"

    NavHost(navController, startDestination = startDestination) {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("home") { HomeScreen(navController) }
    }
}
