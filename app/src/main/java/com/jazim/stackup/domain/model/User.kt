package com.jazim.stackup.domain.model

data class User(
    val id: Int,
    val displayName: String,
    val reputation: Int,
    val profileImage: String,
    val location: String?,
    val websiteUrl: String?,
    val isFollowed: Boolean = false
)