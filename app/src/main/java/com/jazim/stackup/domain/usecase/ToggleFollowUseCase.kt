package com.jazim.stackup.domain.usecase

import com.jazim.stackup.domain.model.User
import com.jazim.stackup.domain.repository.UsersRepository
import javax.inject.Inject

class ToggleFollowUseCase @Inject constructor(
    private val usersRepositoryImpl: UsersRepository
) {
    suspend operator fun invoke(userId: Int, newFollowedState: Boolean): Result<Unit> {
        return usersRepositoryImpl.toggleFollow(userId, newFollowedState)
    }
}