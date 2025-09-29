package com.jazim.stackup.presentation.detailScreen

import android.annotation.SuppressLint
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jazim.stackup.presentation.ui.components.ErrorScreen
import com.jazim.stackup.presentation.ui.components.LoadingScreen
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailMainScreen(
    detailViewModel: DetailViewModel,
) {
    val singleUserState = detailViewModel.singleUserState.collectAsStateWithLifecycle()

    when (val state = singleUserState.value) {
        is SingleUserState.Loading -> {
            LoadingScreen()
        }
        is SingleUserState.Error -> { ErrorScreen(state.message) }
        is SingleUserState.Success -> {
            UserDetailScreen(
                user = state.user,
                onFollowed = { detailViewModel.toggleFollow(state.user) }
            )
        }
    }
}

