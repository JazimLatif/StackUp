package com.jazim.stackup.domain.repository

import com.jazim.stackup.domain.model.User

interface UsersRepository {
    suspend fun get(): Result<List<User>>
}