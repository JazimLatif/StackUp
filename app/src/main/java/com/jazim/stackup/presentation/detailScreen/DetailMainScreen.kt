package com.jazim.stackup.presentation.detailScreen

import android.annotation.SuppressLint
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jazim.stackup.presentation.ui.components.ErrorScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailMainScreen(
    detailViewModel: DetailViewModel,
) {
    val state by detailViewModel.singleUser.collectAsStateWithLifecycle()

    when (state) {
        is SingleUserState.Loading -> { CircularProgressIndicator() }
        is SingleUserState.Error -> { ErrorScreen((state as SingleUserState.Error).throwable)}
        is SingleUserState.Success -> { UserDetailScreen((state as SingleUserState.Success).user, { })}
    }
}

