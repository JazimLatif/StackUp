package com.jazim.stackup.data.repository

import com.jazim.pixelnews.data.networking.ApiService
import com.jazim.stackup.data.datastore.FollowingDataStore
import com.jazim.stackup.data.toDomainModel
import com.jazim.stackup.domain.model.User
import com.jazim.stackup.domain.repository.UsersRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val datastore: FollowingDataStore
): UsersRepository {

    override suspend fun getUsers(): Result<List<User>> {
        return try {
            val response = apiService.getUsers()
            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse != null) {
                    val followedIds = datastore.getAllFollowedUserIds().first()

                    Result.success(apiResponse.toDomainModel(followedIds = followedIds))
                } else {
                    Result.failure(Throwable("Response body is null"))
                }
            } else {
                Result.failure(Throwable("Failed with code: ${response.code()}"))
            }
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun toggleFollow(id: Int, isFollowed: Boolean): Result<Unit> {
        return try {
            datastore.toggleFollow(id, isFollowed)
            Result.success(Unit)
        } catch(e: Exception) {
            Result.failure(e)
        }

    }


}
