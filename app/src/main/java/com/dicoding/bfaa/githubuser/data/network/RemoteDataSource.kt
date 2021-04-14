package com.dicoding.bfaa.githubuser.data.network

import com.dicoding.bfaa.githubuser.BuildConfig
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val retrofit: GithubServices
) {
    private val personalAuthKey: String = BuildConfig.GITHUB_PERSONAL_TOKEN

    suspend fun searchUser(query: String) = retrofit.searchUsers(query, personalAuthKey)

    suspend fun fetchUser(username: String) =
        retrofit.getUserDetailByUsername(username, personalAuthKey)

    suspend fun fetchRepositoriesByUsername(username: String) =
        retrofit.getRepositoriesByUsername(username, personalAuthKey)

    suspend fun fetchFollowersByUsername(username: String) =
        retrofit.getUserFollowersByUsername(username, personalAuthKey)


    suspend fun fetchFollowingByUsername(username: String) =
        retrofit.getUserFollowingByUsername(username, personalAuthKey)

}