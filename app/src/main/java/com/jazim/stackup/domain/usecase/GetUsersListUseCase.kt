package com.jazim.stackup.domain.usecase

import com.jazim.stackup.domain.model.User
import com.jazim.stackup.domain.repository.UsersRepository
import javax.inject.Inject

class GetUsersListUseCase @Inject constructor(
    private val usersRepositoryImpl: UsersRepository
) {
    suspend operator fun invoke(
        page: Int,
        pageSize: Int,
        order: String,
        sort: String,
        site: String
    ): Result<List<User>> {
        return try {
            usersRepositoryImpl.getUsers(
                page,
                pageSize,
                order,
                sort,
                site
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}