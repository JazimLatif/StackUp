package com.jazim.pixelnews.data.networking

import com.jazim.stackup.data.models.UsersResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    //TODO change this to queries
    @GET("2.2/users?page=1&pagesize=20&order=desc&sort=reputation&site=stackoverflow")
    suspend fun getUsers(): Response<UsersResponse>

}