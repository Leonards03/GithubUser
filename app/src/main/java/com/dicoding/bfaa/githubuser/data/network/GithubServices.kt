package com.dicoding.bfaa.githubuser.data.network

import com.dicoding.bfaa.githubuser.data.network.responses.RepositoryResponse
import com.dicoding.bfaa.githubuser.data.network.responses.SearchResponse
import com.dicoding.bfaa.githubuser.data.network.responses.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubServices {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String
    ): SearchResponse

    @GET("users/{username}")
    suspend fun getUserDetailByUsername(
        @Path("username") username: String
    ): UserResponse

    @GET("users/{username}/followers")
    suspend fun getUserFollowersByUsername(
        @Path("username") username: String
    ): List<UserResponse>

    @GET("users/{username}/following")
    suspend fun getUserFollowingByUsername(
        @Path("username") username: String
    ): List<UserResponse>

    @GET("users/{username}/repos")
    suspend fun getRepositoriesByUsername(
        @Path("username") username: String
    ): List<RepositoryResponse>
}