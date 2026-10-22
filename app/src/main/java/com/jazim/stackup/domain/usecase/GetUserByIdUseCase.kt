package com.jazim.stackup.domain.usecase

import com.jazim.stackup.data.model.User
import com.jazim.stackup.data.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val usersRepositoryImpl: UsersRepository
) {
    operator fun invoke(userId: Int): Flow<User> = usersRepositoryImpl.getUserById(userId)

}