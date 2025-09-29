package com.jazim.stackup.presentation.users

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jazim.stackup.domain.model.User
import com.jazim.stackup.domain.repository.UsersRepository
import com.jazim.stackup.domain.usecase.GetUsersListUseCase
import com.jazim.stackup.domain.usecase.ToggleFollowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersListUseCase: GetUsersListUseCase,
    private val toggleFollowUseCase: ToggleFollowUseCase,
    repository: UsersRepository
) : ViewModel() {

    private val _pageNumberState = MutableStateFlow(1)
    val pageNumberState: StateFlow<Int> = _pageNumberState.asStateFlow()

    val usersState: StateFlow<UsersUiState> =
        repository.usersFlow
            .map <List<User>,  UsersUiState>{UsersUiState.Success(it) }
            .catch {
                emit(UsersUiState.Error("Couldn't load users"))
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UsersUiState.Loading)


    init {
        viewModelScope.launch {
            pageNumberState
                .collect { page ->
                    getUsersListUseCase(page).collect()
                }
        }
    }

    fun toggleFollow(user: User) {
        viewModelScope.launch {
            toggleFollowUseCase(user.id, !user.isFollowed)
                .onFailure { e ->
                    Log.e("UsersViewModel", "Failed to update follow state", e)
                }
        }
    }

    fun nextPage() {
        _pageNumberState.update { (it + 1).coerceAtMost(25) }
    }

    fun previousPage() {
        _pageNumberState.update { (it - 1).coerceAtLeast(1) }
    }
}

sealed class UsersUiState {
    data object Loading : UsersUiState()
    data class Success(val users: List<User>) : UsersUiState()
    data class Error(val message: String) : UsersUiState()
}
