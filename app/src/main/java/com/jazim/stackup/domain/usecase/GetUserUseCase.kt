package com.jazim.stackup.domain.usecase

import com.jazim.stackup.domain.model.User
import com.jazim.stackup.domain.repository.UsersRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val usersRepositoryImpl: UsersRepository
) {
    suspend operator fun invoke(userId: Int): Result<User> {
        return try {
            usersRepositoryImpl.getUser(userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}