package com.jazim.stackup.presentation.detailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jazim.stackup.domain.model.User
import com.jazim.stackup.domain.usecase.GetUserUseCase
import com.jazim.stackup.domain.usecase.ToggleFollowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val toggleFollowUseCase: ToggleFollowUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val userId: Int = checkNotNull(savedStateHandle["userId"])

    var singleUser = MutableStateFlow<SingleUserState>(SingleUserState.Loading)
    private set

    init {
        viewModelScope.launch {
            getUserById(userId)
        }
    }

    suspend fun getUserById(userId: Int) {
        val result = getUserUseCase.invoke(userId)
        result.fold(
            onSuccess = { singleUser.value = SingleUserState.Success(it) },
            onFailure = { singleUser.value = SingleUserState.Error(it) }
        )
    }

    suspend fun toggleFollow (user: User) {
        toggleFollowUseCase.invoke(user.id, !user.isFollowed)
    }
}

sealed class SingleUserState() {
    data object Loading: SingleUserState()
    data class Success(val user: User): SingleUserState()
    data class Error(val throwable: Throwable): SingleUserState()
}
