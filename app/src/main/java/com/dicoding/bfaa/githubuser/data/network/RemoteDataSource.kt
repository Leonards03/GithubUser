package com.dicoding.bfaa.githubuser.data.network

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val retrofit: GithubServices
) {

    suspend fun searchUser(query: String) = retrofit.searchUsers(query)

    suspend fun fetchUser(username: String) =
        retrofit.getUserDetailByUsername(username)

    suspend fun fetchRepositoriesByUsername(username: String) =
        retrofit.getRepositoriesByUsername(username)

    suspend fun fetchFollowersByUsername(username: String) =
        retrofit.getUserFollowersByUsername(username)


    suspend fun fetchFollowingByUsername(username: String) =
        retrofit.getUserFollowingByUsername(username)

}