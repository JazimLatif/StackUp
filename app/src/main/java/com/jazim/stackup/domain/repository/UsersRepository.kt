package com.jazim.stackup.domain.repository

import com.jazim.stackup.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UsersRepository {
    val usersFlow: StateFlow<List<User>>
    fun getUsers(
        page: Int,
        pageSize: Int,
        order: String,
        sort: String,
        site: String
    ): Flow<List<User>>
    fun getUserById(userId: Int): Flow<User>
    suspend fun toggleFollow(id: Int, newFollowedState: Boolean): Result<Unit>

}