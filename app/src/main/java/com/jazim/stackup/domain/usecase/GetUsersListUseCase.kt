package com.jazim.stackup.domain.usecase

import com.jazim.stackup.domain.repository.UsersRepository
import javax.inject.Inject

class GetUsersListUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
) {
    operator fun invoke(
        page: Int = 1,
        pageSize: Int = 20,
        order: String = "desc",
        sort: String = "reputation",
        site: String = "stackoverflow"
    ) = usersRepository.getUsers(
        page = page,
        pageSize = pageSize,
        order = order,
        sort = sort,
        site = site
    )

}