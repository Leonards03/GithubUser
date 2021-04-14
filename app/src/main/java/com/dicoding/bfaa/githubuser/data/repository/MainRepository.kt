package com.dicoding.bfaa.githubuser.data.repository

import com.dicoding.bfaa.githubuser.data.local.LocalDataSource
import com.dicoding.bfaa.githubuser.data.mapper.Mapper
import com.dicoding.bfaa.githubuser.data.model.Repository
import com.dicoding.bfaa.githubuser.data.model.User
import com.dicoding.bfaa.githubuser.data.network.RemoteDataSource
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val mapper: Mapper
) {
    suspend fun searchUser(query: String): List<User> {
        val response = remoteDataSource.searchUser(query)
        val result = response.results
        return mapper.userMapper.mapFromResponses(result)
    }

    suspend fun fetchUser(username: String): User {
        val response = remoteDataSource.fetchUser(username)
        return mapper.userMapper.mapFromResponse(response)
    }

    suspend fun fetchFollowersByUsername(username: String): List<User> {
        val response = remoteDataSource.fetchFollowersByUsername(username)
        return mapper.userMapper.mapFromResponses(response)
    }

    suspend fun fetchFollowingByUsername(username: String): List<User> {
        val response = remoteDataSource.fetchFollowingByUsername(username)
        return mapper.userMapper.mapFromResponses(response)
    }

    suspend fun fetchRepositoriesByUsername(username: String): List<Repository> {
        val response = remoteDataSource.fetchRepositoriesByUsername(username)
        return mapper.repositoryMapper.mapFromResponses(response)
    }

    suspend fun getFavoriteList() = localDataSource.getFavoriteList()

    suspend fun getUser(username: String) =
        localDataSource.getUser(username).map {
            mapper.userMapper.mapFromEntity(it)
        }

    suspend fun getFollowersByUsername(username: String) =
        localDataSource.getFollowersByUsername(username)

    suspend fun getFollowingByUsername(username: String) =
        localDataSource.getFollowingByUsername(username)

    suspend fun getRepositoriesByUsername(username: String) =
        localDataSource.getRepositoriesByUsername(username)
}