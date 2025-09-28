package com.jazim.stackup.presentation.detailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jazim.stackup.domain.model.User
import com.jazim.stackup.domain.usecase.GetUserUseCase
import com.jazim.stackup.domain.usecase.ToggleFollowUseCase
import com.jazim.stackup.presentation.detailScreen.SingleUserState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val toggleFollowUseCase: ToggleFollowUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val userId: Int = checkNotNull(savedStateHandle["userId"])

    private val _singleUserState = MutableStateFlow<SingleUserState>(Loading)
    val singleUserState = _singleUserState.asStateFlow()

    init {
        viewModelScope.launch {
            getUserById(userId)
        }
    }

    suspend fun getUserById(userId: Int) {
        val result = getUserUseCase.invoke(userId)
        result.fold(
            onSuccess = { _singleUserState.value = Success(it) },
//            onFailure = { _singleUserState.value = Error(ErrorState(it.message))}
            onFailure = { _singleUserState.value = Error(it.message!!) }
        )
    }

    suspend fun toggleFollow (user: User) {
        toggleFollowUseCase.invoke(user.id, !user.isFollowed)
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