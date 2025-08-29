package com.jazim.stackup.data

import com.jazim.stackup.data.models.UserDto
import com.jazim.stackup.data.models.UsersResponse
import com.jazim.stackup.domain.model.User

fun UserDto.toDomainModel(isFollowed: Boolean = false): User {
    return User(
        id = userId,
        displayName = displayName,
        reputation = reputation,
        profileImage = profileImage ?: "",
        location = location ?: "",
        websiteUrl = websiteUrl ?: "",
        isFollowed = isFollowed
    )
}

fun UsersResponse.toDomainModel(followedIds: Set<Int> = emptySet()): List<User> {
    return items.map { dto ->
        dto.toDomainModel(isFollowed = followedIds.contains(dto.userId))
    }
}