package com.jazim.stackup.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jazim.stackup.domain.model.User
import com.jazim.stackup.domain.usecase.GetUsersListUseCase
import com.jazim.stackup.domain.usecase.ToggleFollowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersList: GetUsersListUseCase,
    private val toggleFollow: ToggleFollowUseCase
): ViewModel() {

    var usersState = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    private set

    init {
        getUsers()
    }
    fun getUsers() {
        viewModelScope.launch {
            val result = getUsersList.invoke()
            result.fold(
                onSuccess = {
                    usersState.value = UsersUiState.Success(it)
                },
                onFailure = {
                    usersState.value = UsersUiState.Error(it)
                }
            )
        }
    }
    fun toggleFollow(user: User) {
        viewModelScope.launch {
            toggleFollow.invoke(user.id, !user.isFollowed)
        }
    }
}

sealed class UsersUiState() {
    data object Loading: UsersUiState()
    data class Success(val users: List<User>): UsersUiState()
    data class Error(val t: Throwable): UsersUiState()
}