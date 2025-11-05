package com.jazim.stackup.presentation.users

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jazim.stackup.domain.model.User
import com.jazim.stackup.domain.usecase.ObserveUsersUseCase
import com.jazim.stackup.domain.usecase.RefreshUsersUseCase
import com.jazim.stackup.domain.usecase.ToggleFollowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val observeUsersUseCase: ObserveUsersUseCase,
    private val refreshUsersUseCase: RefreshUsersUseCase,
    private val toggleFollowUseCase: ToggleFollowUseCase,
) : ViewModel() {

    private val _pageNumberState = MutableStateFlow(1)
    val pageNumberState: StateFlow<Int> = _pageNumberState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    private val _isLoading = MutableStateFlow(false)

    val usersState: StateFlow<UsersUiState> = combine(
        observeUsersUseCase(),
        _errorState,
        _isLoading  // Add this
    ) { users, error, isLoading ->
        when {
            error != null -> UsersUiState.Error(error)
            isLoading -> UsersUiState.Loading
            users.isEmpty() -> UsersUiState.Loading
            else -> UsersUiState.Success(users)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UsersUiState.Loading
    )

    init {
        viewModelScope.launch {
            _pageNumberState.collectLatest { page ->
                _isLoading.value = true
                refreshUsersUseCase(page)
                    .onFailure { e ->
                        _errorState.value = "Couldn't load users: ${e.message}"
                    }
                    .onSuccess {
                        _errorState.value = null
                    }
                _isLoading.value = false
            }
        }
    }

    fun toggleFollow(user: User) {
        viewModelScope.launch {
            toggleFollowUseCase(user.id, !user.isFollowed)
                .onFailure { e ->
                    Log.e("UsersViewModel", "Failed to toggle follow", e)
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
