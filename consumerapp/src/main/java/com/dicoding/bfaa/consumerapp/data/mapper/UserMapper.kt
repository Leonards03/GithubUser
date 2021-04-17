package com.dicoding.bfaa.consumerapp.data.mapper

import com.dicoding.bfaa.consumerapp.data.local.entity.FollowersEntity
import com.dicoding.bfaa.consumerapp.data.local.entity.FollowingEntity
import com.dicoding.bfaa.consumerapp.data.local.entity.UserEntity
import com.dicoding.bfaa.consumerapp.data.model.User
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class UserMapper : ModelMapper<User, UserEntity> {
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
            name = valueOrEmpty(model.name),
            bio = valueOrEmpty(model.bio),
            avatar = model.avatar,
            location = valueOrEmpty(model.location),
            company = valueOrEmpty(model.company),
            followersCount = model.followersCount!!,
            followingCount = model.followingCount!!
        )

    private fun mapFromFollowersEntity(entity: FollowersEntity): User =
        User(
            username = entity.username,
            avatar = entity.avatar
        )

    fun mapFromFollowersEntities(entities: List<FollowersEntity>): List<User> =
        entities.map { entity -> mapFromFollowersEntity(entity) }

    private fun mapFromFollowingEntity(entity: FollowingEntity): User =
        User(
            username = entity.username,
            avatar = entity.avatar
        )

    fun mapFromFollowingEntities(entities: List<FollowingEntity>): List<User> =
        entities.map { entity -> mapFromFollowingEntity(entity) }

    private fun <T : Any> mapToFollowEntity(
        username: String,
        model: User,
        returnClass: KClass<T>
    ): T {
        return when (returnClass) {
            FollowingEntity::class -> FollowingEntity(username, model.username, model.avatar) as T
            else -> FollowersEntity(username, model.username, model.avatar) as T
        }
    }

    fun <T : Any> mapToFollowEntities(
        username: String,
        models: List<User>,
        returnClass: KClass<T>
    ): List<T> = models.map { model -> mapToFollowEntity(username, model, returnClass) }

    fun mapFromEntities(entities: List<UserEntity>): List<User> =
        entities.map { entity -> mapFromEntity(entity) }

    private fun valueOrEmpty(value: String?): String {
        return value ?: String()
    }
}