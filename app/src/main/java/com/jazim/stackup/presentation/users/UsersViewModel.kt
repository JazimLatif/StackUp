package com.jazim.stackup.presentation.users

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jazim.stackup.data.repository.UsersRepositoryImpl
import com.jazim.stackup.domain.model.User
import com.jazim.stackup.domain.usecase.GetUsersListUseCase
import com.jazim.stackup.domain.usecase.ToggleFollowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersListUseCase: GetUsersListUseCase,
    private val toggleFollowUseCase: ToggleFollowUseCase,
    private val repository: UsersRepositoryImpl
) : ViewModel() {

    private val _pageNumberState = MutableStateFlow(1)
    val pageNumberState: StateFlow<Int> = _pageNumberState.asStateFlow()

    // Hot flow the UI observes
    val usersState: StateFlow<UsersUiState> =
        repository.usersFlow
            .map<List<User>, UsersUiState> { UsersUiState.Success(it) }
            .catch { emit(UsersUiState.Error("Couldn't load users")) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UsersUiState.Loading)

    init {
        // Trigger initial and subsequent page loads whenever page number changes
        viewModelScope.launch {
            pageNumberState.collect { page ->
                refresh(page)
            }
        }
    }

    /** Force a network refresh for the current page. */
    fun refresh(page: Int = pageNumberState.value) {
        viewModelScope.launch {
            getUsersListUseCase(
                page = page,
                pageSize = 20,
                order = "desc",
                sort = "reputation",
                site = "stackoverflow"
            )
            .catch { e ->
                // Optional: surface a transient error state
                // e.g. _snackbar.tryEmit("Failed to refresh: ${e.message}")
            }
            .collect()
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
