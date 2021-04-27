package com.dicoding.bfaa.githubuser.data.mapper

import android.database.Cursor
import com.dicoding.bfaa.githubuser.data.local.entity.FollowersEntity
import com.dicoding.bfaa.githubuser.data.local.entity.FollowingEntity
import com.dicoding.bfaa.githubuser.data.local.entity.UserEntity
import com.dicoding.bfaa.githubuser.data.model.User
import com.dicoding.bfaa.githubuser.data.network.responses.UserResponse
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
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

    private fun <T : Any> mapToFollowEntity(username: String, model: User, returnClass: KClass<T>): T {
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

    private fun mapToModel(cursor: Cursor): User{
        cursor.apply {
            return User(
                getString(getColumnIndex("username")),
                getString(getColumnIndex("name")),
                getString(getColumnIndex("bio")),
                getString(getColumnIndex("avatar")),
                getString(getColumnIndex("location")),
                getString(getColumnIndex("company")),
                getInt(getColumnIndex("followers_count")),
                getInt(getColumnIndex("following_count"))
            )
        }
    }

    fun mapToModels(cursor: Cursor): List<User>{
        val userList = ArrayList<User>()
        cursor.apply {
            while (moveToNext())
                userList.add(
                    mapToModel(this)
                )
        }
        return userList
    }

    private fun valueOrEmpty(value: String?): String {
        return value ?: String()
    }
}