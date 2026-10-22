package com.jazim.stackup.data.dto

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("items")
    val items: List<UserDto>
)