package com.jazim.stackup.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jazim.stackup.presentation.navigation.NavigationRoot

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StackUpApp() {
    Scaffold {  innerPadding ->
        NavigationRoot(
            Modifier.padding(innerPadding)
        )
    }
}