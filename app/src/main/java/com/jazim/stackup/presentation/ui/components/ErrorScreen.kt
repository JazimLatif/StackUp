package com.jazim.stackup.presentation.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun ErrorScreen(t: Throwable) {
    Text(t.message!!)
}