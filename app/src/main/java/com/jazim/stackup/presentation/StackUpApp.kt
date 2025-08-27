package com.jazim.stackup.presentation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.jazim.stackup.presentation.navigation.Navigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StackUpApp() {
    val navController = rememberNavController()
    Scaffold {
        Navigation(navController)
    }
}