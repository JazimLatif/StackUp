package com.jazim.stackup.presentation.users

import android.R.attr.order
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.util.CoilUtils.result
import com.jazim.stackup.domain.model.User
import com.jazim.stackup.domain.usecase.GetUsersListUseCase
import com.jazim.stackup.domain.usecase.ToggleFollowUseCase
import com.jazim.stackup.presentation.detailScreen.SingleUserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    getUsersListUseCase: GetUsersListUseCase,
    private val toggleFollowUseCase: ToggleFollowUseCase
): ViewModel() {

    private val _pageNumberState = MutableStateFlow(1)
    val pageNumberState = _pageNumberState.asStateFlow()

    val usersState: StateFlow<UsersUiState> =
        getUsersListUseCase(
            pageNumberState.value,
            20,
            "desc",
            "reputation",
            "stackoverflow"

        )
            .map <List<User>, UsersUiState >{ UsersUiState.Success(it) }
            .catch { emit(UsersUiState.Error("Couldn't load")) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UsersUiState.Loading)

    fun toggleFollow(user: User) {
        viewModelScope.launch {
            val result = toggleFollowUseCase(user.id, !user.isFollowed)
            result.fold(
                onSuccess = {
                    Log.e(
                        javaClass.name,
                        "updated FollowState: ${!user.isFollowed}"
                    )
                },
                onFailure = {
                    Log.e(
                        javaClass.name,
                        "Failed to update follow state: ${result.exceptionOrNull()}"
                    )
                }
            )
        }
    }

    fun nextPage() {
        _pageNumberState.update { it.coerceAtMost(24) + 1 }
    }

    fun previousPage() {
        _pageNumberState.update { it.coerceAtLeast(2) - 1 }
    }
}

sealed class UsersUiState {
    data object Loading: UsersUiState()
    data class Success(val users: List<User>): UsersUiState()
    data class Error(val message: String): UsersUiState()
}