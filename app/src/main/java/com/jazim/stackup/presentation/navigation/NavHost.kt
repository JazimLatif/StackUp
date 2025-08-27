package com.jazim.stackup.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.jazim.stackup.presentation.users.UsersScreen
import com.jazim.stackup.presentation.users.UsersViewModel

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController,
        Screen.UsersScreen.route
    ) {
        composable(route = Screen.UsersScreen.route) {
            val usersViewModel = hiltViewModel<UsersViewModel>()
            UsersScreen(usersViewModel)
        }
    }
}