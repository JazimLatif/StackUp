package com.jazim.stackup.domain.usecase

import com.jazim.stackup.domain.repository.UsersRepository
import javax.inject.Inject

class RefreshUsersUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
) {
    suspend operator fun invoke(
        page: Int,
        pageSize: Int = 20,
        order: String = "desc",
        sort: String = "reputation",
        site: String = "stackoverflow"
    ): Result<Unit> = usersRepository.refreshUsers(page, pageSize, order, sort, site)
}