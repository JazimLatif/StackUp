package com.jazim.stackup.domain.repository

import com.jazim.stackup.domain.model.User

interface UsersRepository {
    suspend fun getUsers(): Result<List<User>>
    suspend fun toggleFollow(id: Int, newFollowedState: Boolean): Result<Unit>
}