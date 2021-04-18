package com.dicoding.bfaa.consumerapp.data.repository

import android.content.Context
import com.dicoding.bfaa.consumerapp.data.local.LocalDataSource
import com.dicoding.bfaa.consumerapp.data.mapper.RepositoryMapper
import com.dicoding.bfaa.consumerapp.data.mapper.UserMapper
import com.dicoding.bfaa.consumerapp.data.model.Repository
import com.dicoding.bfaa.consumerapp.data.model.User
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val userMapper: UserMapper,
    private val repositoryMapper: RepositoryMapper
) {

    fun getFavoriteList(context: Context): List<User> {
        val cursor = localDataSource.getFavoriteUsers(context)
        val userList = ArrayList<User>()

        cursor?.apply {
            while (moveToNext()) {
                val user = userMapper.mapFromEntity(this)
                userList.add(user)
            }
        }
        return userList
    }

    fun getUser(username: String, context: Context): User {
        val cursor = localDataSource.getUser(username, context)
        cursor?.apply {
            while (moveToNext())
                return userMapper.mapFromEntity(this)
        }
        return User("N/A", "N/A")
    }

    fun getFollowers(username: String, context: Context): List<User> {
        val cursor = localDataSource.getFollowers(username, context)
        val userList = ArrayList<User>()
        cursor?.apply {
            while (moveToNext()) {
                val user = userMapper.mapFromFollowEntity(this)
                userList.add(user)
            }
        }
        return userList
    }

    fun getFollowing(username: String, context: Context): List<User> {
        val cursor = localDataSource.getFollowing(username, context)
        val userList = ArrayList<User>()

        cursor?.apply {
            while (moveToNext()) {
                val user = userMapper.mapFromFollowEntity(this)
                userList.add(user)
            }
        }
        return userList
    }

    fun getRepositories(username: String, context: Context): List<Repository> {
        val cursor = localDataSource.getRepositories(username, context)
        val repositories = ArrayList<Repository>()

        cursor?.apply {
            while (moveToNext()) {
                val repo = repositoryMapper.mapFromEntity(this)
                repositories.add(repo)
            }
        }
        return repositories
    }

    fun removeUserFromFavorite(username: String, context: Context) =
        localDataSource.removeUserFromFavorite(username, context)
}