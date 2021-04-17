package com.dicoding.bfaa.githubuser.data.local

import com.dicoding.bfaa.githubuser.data.local.dao.UserDao
import com.dicoding.bfaa.githubuser.data.local.dao.RepositoryDao
import com.dicoding.bfaa.githubuser.data.local.entity.FollowersEntity
import com.dicoding.bfaa.githubuser.data.local.entity.FollowingEntity
import com.dicoding.bfaa.githubuser.data.local.entity.RepositoryEntity
import com.dicoding.bfaa.githubuser.data.local.entity.UserEntity

class LocalDataSource(
    private val userDao: UserDao,
    private val repositoryDao: RepositoryDao
) {

    suspend fun getFavoriteList() = userDao.getFavoriteList()

    fun getFavoriteCursor() = userDao.getFavoriteCursor()

    suspend fun getUser(username: String) = userDao.getUser(username)

    fun getUserCursor(username: String) = userDao.getUserCursor(username)

    suspend fun favoriteUser(user: UserEntity) = userDao.insertUser(user)

    suspend fun removeUserFromFavorite(user: UserEntity) = userDao.removeUser(user)

    suspend fun getFollowersByUsername(username: String) =
        userDao.getFollowers(username)

    suspend fun addUserFollowers(followers: List<FollowersEntity>) =
        userDao.insertFollowers(followers)

    suspend fun getFollowingByUsername(username: String) = userDao.getFollowings(username)

    suspend fun addUserFollowing(following: List<FollowingEntity>) = userDao.insertFollowing(following)

    suspend fun getRepositoriesByUsername(username: String) =
        repositoryDao.getRepositories(username)

    suspend fun addUserRepositories(repositories: List<RepositoryEntity>) =
        repositoryDao.insertRepositories(repositories)

}