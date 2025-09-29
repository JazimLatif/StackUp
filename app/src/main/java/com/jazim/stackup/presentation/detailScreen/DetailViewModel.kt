package com.jazim.stackup.presentation.detailScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jazim.stackup.domain.model.User
import com.jazim.stackup.domain.usecase.GetUserByIdUseCase
import com.jazim.stackup.domain.usecase.ToggleFollowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    getUserByIdUseCase: GetUserByIdUseCase,
    private val toggleFollowUseCase: ToggleFollowUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val userId: Int = checkNotNull(savedStateHandle["userId"])

    val singleUserState : StateFlow<SingleUserState> =
        getUserByIdUseCase(userId)
            .map<User, SingleUserState >{ SingleUserState.Success(it) }
            .catch { emit(SingleUserState.Error("Couldn't load user")) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SingleUserState.Loading)


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
}

sealed class SingleUserState {
    data object Loading: SingleUserState()
    data class Success(val user: User): SingleUserState()
//    data class Error(val errorState: ErrorState): SingleUserState()
    data class Error(val message: String): SingleUserState()
}

//
//sealed class ErrorState(val message: String) {
//    data class NetworkError(val message: String): SingleUserState()
//    data class UnknownError(val message: String): SingleUserState()
//}