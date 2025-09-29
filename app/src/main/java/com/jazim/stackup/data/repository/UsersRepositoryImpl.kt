package com.jazim.stackup.data.repository

import android.R.attr.order
import com.jazim.stackup.data.datastore.FollowingDataStore
import com.jazim.stackup.data.networking.ApiService
import com.jazim.stackup.data.toDomainModel
import com.jazim.stackup.domain.model.Badges
import com.jazim.stackup.domain.model.User
import com.jazim.stackup.domain.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.collections.emptyList

class UsersRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val datastore: FollowingDataStore
): UsersRepository {

    val fakeUserList = listOf(User(1,"foobar", 200, "", "", "", Badges(1, 2, 5)),User(2,"foobar", 400, "", "", "",Badges(1, 2, 5)))

            private val _usersFlow = MutableStateFlow<List<User>>(fakeUserList)
    override val usersFlow: StateFlow<List<User>> = _usersFlow.asStateFlow()

    override fun getUsers(
        page: Int,
        pageSize: Int,
        order: String,
        sort: String,
        site: String
    ): Flow<List<User>> = flow {
        val response = apiService.getUsers(
            page,
            pageSize,
            order,
            sort,
            site
        )
        if (response.isSuccessful) {
            val followedIds = datastore.getAllFollowedUserIds().first()
            val users = response.body()?.toDomainModel(followedIds) ?: emptyList()
            _usersFlow.value = users
            emit(users)
        } else {
            throw Exception("Failed to fetch users: ${response.errorBody()?.string()}")
        }
    }


    override fun getUserById(userId: Int): Flow<User> =
        _usersFlow.mapNotNull { users ->
            users.find { it.id == userId }
        }

    override suspend fun toggleFollow(id: Int, newFollowedState: Boolean): Result<Unit> {
        return try {
            datastore.toggleFollow(id, newFollowedState)
            _usersFlow.update { users ->
                users.map { user ->
                    if (user.id == id) user.copy(isFollowed = newFollowedState) else user
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}