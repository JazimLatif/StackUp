package com.jazim.stackup.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jazim.stackup.domain.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersRepository: UsersRepository
): ViewModel() {

    private val _usersState = MutableStateFlow("userState")
    val usersState = _usersState.asStateFlow()



    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                usersRepository.get()
            }
        }
    }
}