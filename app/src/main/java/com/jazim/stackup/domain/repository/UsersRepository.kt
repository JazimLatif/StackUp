package com.jazim.stackup.domain.repository

import com.jazim.stackup.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UsersRepository {
    val usersFlow: StateFlow<List<User>>

    /**
     * Cold flow—collecting it triggers a network fetch and emits the
     * freshly merged list once. Also updates [usersFlow].
     */
    fun getUsers(
        page: Int,
        pageSize: Int,
        order: String,
        sort: String,
        site: String
    ): Flow<List<User>>

    /** Observe a single user from the current list. */
    fun getUserById(userId: Int): Flow<User>
    suspend fun toggleFollow(id: Int, newFollowedState: Boolean): Result<Unit>

}