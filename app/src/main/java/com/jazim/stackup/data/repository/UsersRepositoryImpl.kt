package com.jazim.stackup.data.repository

import com.jazim.stackup.data.networking.ApiService
import com.jazim.stackup.data.datastore.FollowingDataStore
import com.jazim.stackup.data.toDomainModel
import com.jazim.stackup.domain.model.User
import com.jazim.stackup.domain.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val datastore: FollowingDataStore
): UsersRepository {

    // Thought about adding Dispatchers IO here, but after looking into it, it's not needed
    // https://developer.android.com/kotlin/coroutines/coroutines-adv
    // (It is needed for toggleFollow though)
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

    override suspend fun toggleFollow(id: Int, newFollowedState: Boolean): Result<Unit> {
        return try {
            // Added here because it involves a write to disk
            withContext(Dispatchers.IO) {
                datastore.toggleFollow(id, newFollowedState)
            }
            datastore.toggleFollow(id, newFollowedState)
            Result.success(Unit)
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

}
