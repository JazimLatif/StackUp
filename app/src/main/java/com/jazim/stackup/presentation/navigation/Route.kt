package com.jazim.stackup.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {

    @Serializable
    data object UsersScreen: Route

    @Serializable
    data class UserDetailScreen(val userId: Int): Route
}