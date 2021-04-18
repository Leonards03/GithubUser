package com.dicoding.bfaa.githubuser.data.repository

import android.database.Cursor
import com.dicoding.bfaa.githubuser.data.local.LocalDataSource
import com.dicoding.bfaa.githubuser.data.local.entity.FollowersEntity
import com.dicoding.bfaa.githubuser.data.local.entity.FollowingEntity
import com.dicoding.bfaa.githubuser.data.mapper.RepositoryMapper
import com.dicoding.bfaa.githubuser.data.mapper.UserMapper
import com.dicoding.bfaa.githubuser.data.model.Repository
import com.dicoding.bfaa.githubuser.data.model.User
import com.dicoding.bfaa.githubuser.data.network.RemoteDataSource
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val userMapper: UserMapper,
    private val repositoryMapper: RepositoryMapper
) {
    suspend fun searchUser(query: String): List<User> {
        val response = remoteDataSource.searchUser(query)
        val result = response.results
        return userMapper.mapFromResponses(result)
    }

    suspend fun fetchUser(username: String): User {
        val response = remoteDataSource.fetchUser(username)
        return userMapper.mapFromResponse(response)
    }

    suspend fun fetchFollowersByUsername(username: String): List<User> {
        val response = remoteDataSource.fetchFollowersByUsername(username)
        return userMapper.mapFromResponses(response)
    }

    suspend fun fetchFollowingByUsername(username: String): List<User> {
        val response = remoteDataSource.fetchFollowingByUsername(username)
        return userMapper.mapFromResponses(response)
    }

    suspend fun fetchRepositoriesByUsername(username: String): List<Repository> {
        val response = remoteDataSource.fetchRepositoriesByUsername(username)
        return repositoryMapper.mapFromResponses(response)
    }

    suspend fun getFavoriteList(): List<User> {
        val entities = localDataSource.getFavoriteList()
        return userMapper.mapFromEntities(entities)
    }

    fun getFavoriteCursor() = localDataSource.getFavoriteCursor()

    suspend fun getUser(username: String): User {
        val user = localDataSource.getUser(username)
        return userMapper.mapFromEntity(user)
    }

    fun getUserCursor(username: String) = localDataSource.getUserCursor(username)

    suspend fun getFollowersByUsername(username: String): List<User> {
        val followersEntity = localDataSource.getFollowersByUsername(username)
        return userMapper.mapFromFollowersEntities(followersEntity)
    }

    fun getFollowersCursor(username: String): Cursor = localDataSource.getFollowersCursor(username)

    suspend fun getFollowingByUsername(username: String): List<User> {
        val followingEntity = localDataSource.getFollowingByUsername(username)
        return userMapper.mapFromFollowingEntities(followingEntity)
    }

    fun getFollowingCursor(username: String): Cursor = localDataSource.getFollowingCursor(username)

    suspend fun getRepositoriesByUsername(username: String): List<Repository> {
        val repositoriesEntity = localDataSource.getRepositoriesByUsername(username)
        return repositoryMapper.mapFromEntities(repositoriesEntity)
    }

    fun getRepositoriesCursor(username: String) = localDataSource.getRepositoriesCursor(username)

    suspend fun addUserToFavorite(
        user: User,
        followers: List<User>,
        following: List<User>,
        repositories: List<Repository>
    ) {
        val userEntity = userMapper.mapToEntity(user)
        val followersEntity = userMapper.mapToFollowEntities(
            user.username,
            followers,
            FollowersEntity::class
        )
        val followingEntity = userMapper.mapToFollowEntities(
            user.username,
            following,
            FollowingEntity::class
        )
        val repositoriesEntity = repositoryMapper.mapToEntities(repositories)

        with(localDataSource) {
            favoriteUser(userEntity)
            addUserFollowers(followersEntity)
            addUserFollowing(followingEntity)
            addUserRepositories(repositoriesEntity)
        }
    }

    suspend fun removeUserFromFavorite(user: User) {
        val userEntity = userMapper.mapToEntity(user)
        localDataSource.removeUserFromFavorite(userEntity)
    }

    fun removeUserFromFavorite(username: String) = localDataSource.removeUserFromFavorite(username)
}