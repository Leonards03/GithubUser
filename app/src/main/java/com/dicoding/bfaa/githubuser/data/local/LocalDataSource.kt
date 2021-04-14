package com.dicoding.bfaa.githubuser.data.local

import com.dicoding.bfaa.githubuser.data.local.dao.RepositoryDao
import com.dicoding.bfaa.githubuser.data.local.dao.UserDao

class LocalDataSource(
    private val userDao: UserDao,
    private val repositoryDao: RepositoryDao
) {

    fun getFavoriteList() = userDao.getFavoriteList()

    fun getUser(username: String) = userDao.getUser(username)

    suspend fun getFollowersByUsername(username: String) {
    }

    suspend fun getFollowingByUsername(username: String) {

    }

    suspend fun getRepositoriesByUsername(username: String) {

    }
}