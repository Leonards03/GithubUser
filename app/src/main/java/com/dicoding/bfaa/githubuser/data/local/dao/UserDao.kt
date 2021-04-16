package com.dicoding.bfaa.githubuser.data.local.dao

import androidx.room.*
import com.dicoding.bfaa.githubuser.data.local.entity.FollowersEntity
import com.dicoding.bfaa.githubuser.data.local.entity.FollowingEntity
import com.dicoding.bfaa.githubuser.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUser(username: String): UserEntity

    @Query("SELECT * FROM users")
    suspend fun getFavoriteList(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun removeUser(user: UserEntity)

    @Query("SELECT * FROM followers WHERE owner = :owner ")
    suspend fun getFollowers(owner: String): List<FollowersEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowers(followers: List<FollowersEntity>)

    @Query("SELECT * FROM following WHERE owner = :owner")
    suspend fun getFollowings(owner: String): List<FollowingEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowing(following: List<FollowingEntity>)

}