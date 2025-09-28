package com.jazim.stackup.presentation.users

import android.R.attr.order
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jazim.stackup.domain.model.User
import com.jazim.stackup.domain.usecase.GetUsersListUseCase
import com.jazim.stackup.domain.usecase.ToggleFollowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersList: GetUsersListUseCase,
    private val toggleFollow: ToggleFollowUseCase
): ViewModel() {

    private val _usersState = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    val usersState = _usersState.asStateFlow()

    private val _pageNumberState = MutableStateFlow(1)
    val pageNumberState = _pageNumberState.asStateFlow()

    init {
        getUsers(pageNumberState.value)
    }
    fun getUsers(
        page: Int = _pageNumberState.value,
        pageSize: Int = 20,
        order: String = "desc",
        sort: String = "reputation",
        site: String = "stackoverflow"
    ) {
        viewModelScope.launch {
            val result = getUsersList.invoke(
                page,
                pageSize,
                order,
                sort,
                site
            )
            result.fold(
                onSuccess = {
                    _usersState.value = UsersUiState.Success(it)
                },
                onFailure = {
                    _usersState.value = UsersUiState.Error(it.message!!)
                }
            )
        }
    }

    fun toggleFollow(user: User) {
        viewModelScope.launch {
            toggleFollow.invoke(user.id, !user.isFollowed)
        }
    }

    fun nextPage() {
        _pageNumberState.update { it.coerceAtMost(24) + 1 }
        getUsers(_pageNumberState.value)
    }

    fun previousPage() {
        _pageNumberState.update { it.coerceAtLeast(2) - 1 }
        getUsers(_pageNumberState.value)
    }
}

sealed class UsersUiState {
    data object Loading: UsersUiState()
    data class Success(val users: List<User>): UsersUiState()
    data class Error(val message: String): UsersUiState()
}