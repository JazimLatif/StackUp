package com.jazim.stackup.presentation.users

import android.annotation.SuppressLint
import android.app.ProgressDialog.show
import android.util.Log.e
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.jazim.stackup.presentation.ui.components.ErrorScreen
import com.jazim.stackup.presentation.ui.components.LoadingScreen
import com.jazim.stackup.presentation.ui.components.UserCard
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UsersMainScreen(
    navController: NavController,
    usersViewModel: UsersViewModel
) {

    val usersState = usersViewModel.usersState.collectAsStateWithLifecycle()

    val pageNumber = usersViewModel.pageNumberState.collectAsStateWithLifecycle()

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    when (val state = usersState.value){
        is UsersUiState.Loading -> {
            LoadingScreen()
        }
        is UsersUiState.Error -> {
            ErrorScreen(state.message)
        }
        is UsersUiState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LazyColumn(
                    modifier = Modifier.testTag("MainScreen").weight(1f),
                    state = listState
                ) {
                    items(state.users) { user ->
                        UserCard(
                            user = user,
                            onFollowClick = {
                                usersViewModel.toggleFollow(user)
                            },
                            onCardClick = { navController.navigate(Screen.UserDetailScreen.route + "/${user.id}") }
                        )
                    }
                }
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        {
                            usersViewModel.previousPage()
                            coroutineScope.launch {
                                listState.animateScrollToItem(0)
                            }
                        },
                        enabled = pageNumber.value >1,
                        shape = RoundedCornerShape(6.dp)
                    ) { Text("< Previous Page")}
                    Button(
                        {
                            usersViewModel.nextPage()
                            coroutineScope.launch {
                                listState.animateScrollToItem(0)
                            }
                        },
                        enabled = pageNumber.value < 25,
                        shape = RoundedCornerShape(6.dp)
                    ) { Text("Next Page >")}
                }
            }
        }
    }
}


