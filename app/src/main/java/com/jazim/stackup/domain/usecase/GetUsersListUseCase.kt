package com.jazim.stackup.domain.usecase

import com.jazim.stackup.domain.model.User
import com.jazim.stackup.domain.repository.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import okhttp3.Dispatcher
import javax.inject.Inject

class GetUsersListUseCase @Inject constructor(
    private val usersRepositoryImpl: UsersRepository,
) {
    operator fun invoke(
        page: Int,
        pageSize: Int,
        order: String,
        sort: String,
        site: String
    ): Flow<List<User>> =
            usersRepositoryImpl.getUsers(
                page,
                pageSize,
                order,
                sort,
                site
            )

}