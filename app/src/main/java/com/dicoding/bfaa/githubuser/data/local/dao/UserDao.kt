package com.dicoding.bfaa.githubuser.data.local.dao

import android.database.Cursor
import androidx.room.*
import com.dicoding.bfaa.githubuser.data.local.GithubDatabase.Companion.FOLLOWERS_TABLE
import com.dicoding.bfaa.githubuser.data.local.GithubDatabase.Companion.FOLLOWING_TABLE
import com.dicoding.bfaa.githubuser.data.local.GithubDatabase.Companion.USERS_TABLE
import com.dicoding.bfaa.githubuser.data.local.entity.FollowersEntity
import com.dicoding.bfaa.githubuser.data.local.entity.FollowingEntity
import com.dicoding.bfaa.githubuser.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM $USERS_TABLE WHERE username = :username")
    suspend fun getUser(username: String): UserEntity

    @Query("SELECT * FROM $USERS_TABLE WHERE username = :username")
    fun getUserCursor(username: String): Cursor

    @Query("SELECT * FROM $USERS_TABLE")
    suspend fun getFavoriteList(): List<UserEntity>

    @Query("SELECT * FROM $USERS_TABLE")
    fun getFavoriteCursor(): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun removeUser(user: UserEntity)

    @Query("DELETE FROM $USERS_TABLE WHERE username = :username")
    fun removeUser(username:String ): Int

    @Query("SELECT * FROM $FOLLOWERS_TABLE WHERE owner = :owner ")
    suspend fun getFollowers(owner: String): List<FollowersEntity>

    @Query("SELECT * FROM $FOLLOWERS_TABLE WHERE owner = :owner ")
    fun getFollowersCursor(owner: String): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowers(followers: List<FollowersEntity>)

    @Query("SELECT * FROM $FOLLOWING_TABLE WHERE owner = :owner")
    suspend fun getFollowings(owner: String): List<FollowingEntity>

    @Query("SELECT * FROM $FOLLOWING_TABLE WHERE owner = :owner")
    fun getFollowingsCursor(owner: String): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowing(following: List<FollowingEntity>)
}