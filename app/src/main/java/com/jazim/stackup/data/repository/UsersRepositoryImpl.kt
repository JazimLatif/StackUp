package com.jazim.stackup.data.repository

import com.jazim.pixelnews.data.networking.ApiService
import com.jazim.stackup.domain.model.User
import com.jazim.stackup.domain.repository.UsersRepository
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): UsersRepository {
    override suspend fun get(): Result<List<User>> {
        return try {
            //TODO fix this to map to use a mapper
            val response = apiService.getUsers()
            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse != null) {
                    Result.success(apiResponse)
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
}
