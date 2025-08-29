package com.jazim.stackup.presentation.state

import com.jazim.stackup.domain.model.User

data class UserUiState(
    val isLoading: Boolean = true,
    val users: List<User> = emptyList(),
    val error: String? = null
)