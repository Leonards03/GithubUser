package com.dicoding.bfaa.githubuser.data.local.dao

import androidx.room.*
import com.dicoding.bfaa.githubuser.data.local.entity.FollowersEntity
import com.dicoding.bfaa.githubuser.data.local.entity.FollowingEntity
import com.dicoding.bfaa.githubuser.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE username = :username")
    fun getUser(username: String): Flow<UserEntity>

    @Query("SELECT * FROM users")
    fun getFavoriteList(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun favoriteUser(user: UserEntity)

    @Delete
    fun unfavoriteUser(user: UserEntity)

    @Query("SELECT * FROM followers")
    fun getFollowers(): List<FollowersEntity>

    @Query("SELECT * FROM following")
    fun getFollowings(): List<FollowingEntity>


}