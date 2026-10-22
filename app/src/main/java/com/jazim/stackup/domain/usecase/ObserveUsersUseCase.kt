package com.jazim.stackup.domain.usecase

import com.jazim.stackup.data.model.User
import com.jazim.stackup.data.repository.UsersRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ObserveUsersUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
) {
    operator fun invoke(): StateFlow<List<User>> = usersRepository.usersFlow
}