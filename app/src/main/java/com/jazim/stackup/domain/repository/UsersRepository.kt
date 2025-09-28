package com.jazim.stackup.domain.repository

import com.jazim.stackup.domain.model.User

interface UsersRepository {
    suspend fun getUsers(
        page: Int,
        pageSize: Int,
        order: String,
        sort: String,
        site: String
    ): Result<List<User>>
    suspend fun getUser(userId: Int): Result<User>
    suspend fun toggleFollow(id: Int, newFollowedState: Boolean): Result<Unit>

}