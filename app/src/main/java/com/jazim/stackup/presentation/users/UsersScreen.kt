package com.jazim.stackup.presentation.users

import android.annotation.SuppressLint
import android.util.Log.e
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.jazim.stackup.R
import com.jazim.stackup.presentation.navigation.Screen
import com.jazim.stackup.presentation.ui.components.LoadingScreen
import com.jazim.stackup.presentation.ui.components.UserCard

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UsersScreen(
    navController: NavController,
    usersViewModel: UsersViewModel
) {

    val usersState by usersViewModel.usersState.collectAsStateWithLifecycle()

    when (usersState){
        is UsersUiState.Loading -> LoadingScreen()
        is UsersUiState.Error -> {}
        is UsersUiState.Success -> {
            LazyColumn(modifier = Modifier.testTag("MainScreen")) {
                items((usersState as UsersUiState.Success).users) { user ->
                    UserCard(
                        user = user,
                        onFollowClick = { usersViewModel.toggleFollow(user) },
                        onCardClick = { navController.navigate(Screen.UserDetailScreen.route + "/${user.id}") }
                    )
                }
            }
        }
    }
}


