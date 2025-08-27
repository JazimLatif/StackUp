package com.jazim.stackup.data.models

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("items")
    val items: List<UserDto>
)