package com.jazim.stackup.presentation.newfeature

import androidx.lifecycle.ViewModel
import com.jazim.stackup.domain.model.User
import com.jazim.stackup.domain.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val usersRepository: UsersRepository
): ViewModel() {

//    var userState = MutableStateFlow<User>()

    init {
//        getUser()
    }


    //findUser () {

    }

//
//    fun getUsers() {
//        viewModelScope.launch {
//            _usersState.value = UserUiState()
//            val result = usersRepository.getUsers()
//
//            _usersState.value = result.fold(
//                onSuccess = { users ->
//                    UserUiState(
//                        isLoading = false,
//                        users = users,
//                        error = null
//                    )
//                },
//                onFailure = { throwable ->
//                    UserUiState(
//                        isLoading = false,
//                        users = emptyList(),
//                        error = throwable.message
//                    )
//                }
//            )
//
//        }
//    }
//
//    fun toggleFollow(user: User) {
//        viewModelScope.launch {
//            val result = usersRepository.toggleFollow(user.id, !user.isFollowed)
//            if (result.isSuccess) {
//                // allows us to update the list in place rather than re fetching from the API
//                // I could also have used a lazyListState but this seems more sensible for the use case
//                // If the follow/unfollow were linked to a network call, this would have to be changed,
//                // however since currently it's on device only, this is a more seamless way of
//                // following and unfollowing users on the device side
//                _usersState.value = _usersState.value.copy(
//                    users = _usersState.value.users.map {
//                        if (it.id == user.id) it.copy(isFollowed = !it.isFollowed)
//                        else it
//                    }
//                )
//            } else {
//                Log.e(javaClass.name, "Failed to update follow state: ${result.exceptionOrNull()}")
//            }
//        }
//    }
