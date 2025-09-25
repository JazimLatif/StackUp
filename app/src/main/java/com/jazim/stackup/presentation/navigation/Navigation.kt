package com.jazim.stackup.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jazim.stackup.presentation.detailScreen.DetailMainScreen
import com.jazim.stackup.presentation.detailScreen.DetailViewModel
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

            UsersScreen(navController, usersViewModel)
        }

        composable(
            route = Screen.UserDetailScreen.route+"/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType})
        ) {
            val id = it.arguments?.getInt("userId")
            val detailViewModel = hiltViewModel<DetailViewModel>()
            DetailMainScreen(detailViewModel)
        }
    }
}