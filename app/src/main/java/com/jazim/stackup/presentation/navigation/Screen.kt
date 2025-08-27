package com.jazim.stackup.presentation.navigation

sealed class Screen(val route: String) {
    object UsersScreen: Screen("usersscreen")
}