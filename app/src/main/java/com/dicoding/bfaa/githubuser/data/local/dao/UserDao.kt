package com.dicoding.bfaa.githubuser.data.local.dao

import android.database.Cursor
import androidx.room.*
import com.dicoding.bfaa.githubuser.data.local.entity.FollowersEntity
import com.dicoding.bfaa.githubuser.data.local.entity.FollowingEntity
import com.dicoding.bfaa.githubuser.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT EXISTS (SELECT * FROM USERS WHERE username = :username)")
    suspend fun isUserExists(username: String): Boolean

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUser(username: String): UserEntity

    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserCursor(username: String): Cursor

    @Query("SELECT * FROM users")
    suspend fun getFavoriteList(): List<UserEntity>

    @Query("SELECT * FROM users")
    fun getFavoriteCursor(): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun removeUser(user: UserEntity)

    @Query("DELETE FROM users WHERE username = :username")
    fun removeUser(username:String ): Int

    @Query("SELECT * FROM followers WHERE owner = :owner ")
    suspend fun getFollowers(owner: String): List<FollowersEntity>

    @Query("SELECT * FROM followers WHERE owner = :owner ")
    fun getFollowersCursor(owner: String): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowers(followers: List<FollowersEntity>)

    @Query("SELECT * FROM following WHERE owner = :owner")
    suspend fun getFollowings(owner: String): List<FollowingEntity>

    @Query("SELECT * FROM following WHERE owner = :owner")
    fun getFollowingsCursor(owner: String): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowing(following: List<FollowingEntity>)

}