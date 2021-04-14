package com.dicoding.bfaa.githubuser.data.mapper

import com.dicoding.bfaa.githubuser.data.local.entity.UserEntity
import com.dicoding.bfaa.githubuser.data.model.User
import com.dicoding.bfaa.githubuser.data.network.responses.UserResponse

class UserMapper : ModelMapper<UserResponse, User, UserEntity> {
    override fun mapFromResponse(response: UserResponse): User =
        User(
            username = response.username,
            name = response.name,
            bio = response.bio,
            avatar = response.avatar,
            location = response.location,
            company = response.company,
            followersCount = response.followers,
            followingCount = response.following
        )

    fun mapFromResponses(responses: List<UserResponse>): List<User> =
        responses.map { response -> mapFromResponse(response) }

    override fun mapFromEntity(entity: UserEntity): User =
        User(
            username = entity.username,
            name = entity.name,
            bio = entity.bio,
            avatar = entity.avatar,
            location = entity.location,
            company = entity.company,
            followersCount = entity.followersCount,
            followingCount = entity.followingCount
        )

    override fun mapToEntity(model: User): UserEntity =
        UserEntity(
            username = model.username,
            name = model.name!!,
            bio = model.bio!!,
            avatar = model.avatar,
            location = model.location!!,
            company = model.company!!,
            followersCount = model.followersCount!!,
            followingCount = model.followingCount!!
        )

    fun mapFromEntities(entities: List<UserEntity>): List<User> =
        entities.map { entity -> mapFromEntity(entity) }
}