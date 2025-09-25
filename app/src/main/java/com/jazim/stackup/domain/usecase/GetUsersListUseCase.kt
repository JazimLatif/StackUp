package com.jazim.stackup.domain.usecase

import com.jazim.stackup.domain.model.User
import com.jazim.stackup.domain.repository.UsersRepository
import javax.inject.Inject

class GetUsersListUseCase @Inject constructor(
    private val usersRepositoryImpl: UsersRepository
) {
    suspend operator fun invoke(): Result<List<User>> {
        return try {
            usersRepositoryImpl.getUsers()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}