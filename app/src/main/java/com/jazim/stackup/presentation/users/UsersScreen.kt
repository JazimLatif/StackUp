package com.jazim.stackup.presentation.users

import android.annotation.SuppressLint
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UsersScreen(
    usersViewModel: UsersViewModel
) {
    Text("${usersViewModel.usersState}")
}