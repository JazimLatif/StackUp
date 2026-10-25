package com.jazim.stackup.presentation.detailScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jazim.stackup.data.model.User
import com.jazim.stackup.domain.usecase.GetUserByIdUseCase
import com.jazim.stackup.domain.usecase.ToggleFollowUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailViewModel.Factory::class)
class DetailViewModel @AssistedInject constructor(
    getUserByIdUseCase: GetUserByIdUseCase,
    private val toggleFollowUseCase: ToggleFollowUseCase,
    @Assisted val userId: Int
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(userId: Int): DetailViewModel
    }

    val singleUserState: StateFlow<SingleUserState> =
        getUserByIdUseCase(userId)
            .map<User, SingleUserState> { SingleUserState.Success(it) }
            .catch { emit(SingleUserState.Error("Couldn't load user")) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SingleUserState.Loading)

    fun toggleFollow(user: User) {
        viewModelScope.launch {
            val result = toggleFollowUseCase(user.id, !user.isFollowed)
            result.fold(
                onSuccess = {
                    Log.d(javaClass.name, "Updated follow state: ${!user.isFollowed}")
                },
                onFailure = {
                    Log.e(javaClass.name, "Failed to update follow state", it)
                }
            )
        }
    }
}

sealed class SingleUserState {
    data object Loading : SingleUserState()
    data class Success(val user: User) : SingleUserState()
    data class Error(val message: String) : SingleUserState()
}