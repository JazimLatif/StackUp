package com.jazim.stackup.data.models

import com.google.gson.annotations.SerializedName

data class BadgeCountsDto(
    @SerializedName("bronze")
    val bronze: Int,
    @SerializedName("silver")
    val silver: Int,
    @SerializedName("gold")
    val gold: Int
)
