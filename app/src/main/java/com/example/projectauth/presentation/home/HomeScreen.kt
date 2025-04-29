package com.example.projectauth.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val secondsLeft by viewModel.secondsLeft.observeAsState(600)
    val context = LocalContext.current

    LaunchedEffect(secondsLeft) {
        if (secondsLeft == 0) {
            Toast.makeText(context, "Sesi berakhir, kembali ke login", Toast.LENGTH_SHORT).show()
            navController.navigate("login", navOptions {
                popUpTo("home") { inclusive = true }
            })
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Selamat datang! Auto logout dalam $secondsLeft detik.")
        }
    }
}
