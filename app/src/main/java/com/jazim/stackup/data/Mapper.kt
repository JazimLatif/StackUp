package com.jazim.stackup.data

import com.jazim.stackup.data.models.BadgeCountsDto
import com.jazim.stackup.data.models.UserDto
import com.jazim.stackup.data.models.UsersResponse
import com.jazim.stackup.domain.model.Badges
import com.jazim.stackup.domain.model.User

fun UserDto.toDomainModel(isFollowed: Boolean = false): User {
    return User(
        id = userId,
        displayName = displayName,
        reputation = reputation,
        profileImage = profileImage ?: "",
        location = location ?: "",
        websiteUrl = websiteUrl ?: "",
        badges = badgeCounts.toDomainModel(),
        isFollowed = isFollowed
    )
}

fun UsersResponse.toDomainModel(followedIds: Set<Int> = emptySet()): List<User> {
    return items.map { dto ->
        dto.toDomainModel(isFollowed = followedIds.contains(dto.userId))
    }
}

fun BadgeCountsDto.toDomainModel(): Badges {
    return Badges(
        bronze = bronze,
        silver = silver,
        gold = gold
    )
}