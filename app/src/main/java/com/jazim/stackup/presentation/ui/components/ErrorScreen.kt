package com.jazim.stackup.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun ErrorScreen(message: String) {
    Box(
        modifier = Modifier.fillMaxSize().testTag("ErrorScreen"),
        contentAlignment = Alignment.Center,

    ) {
        Text(message)
    }
}