package com.jazim.stackup.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.jazim.stackup.presentation.detailScreen.DetailMainScreen
import com.jazim.stackup.presentation.detailScreen.DetailViewModel
import com.jazim.stackup.presentation.users.UsersMainScreen
import com.jazim.stackup.presentation.users.UsersViewModel

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(Route.UsersScreen)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Route.UsersScreen> {
                UsersMainScreen(
                    navigateToUser = { userId -> backStack.add(Route.UserDetailScreen(userId)) },
                    usersViewModel = hiltViewModel<UsersViewModel>()
                )
            }
            entry<Route.UserDetailScreen> { key ->
                DetailMainScreen(
                    hiltViewModel<DetailViewModel, DetailViewModel.Factory>(
                        creationCallback = { factory -> factory.create(key.userId) }
                    )
                )
            }
        }
    )
}