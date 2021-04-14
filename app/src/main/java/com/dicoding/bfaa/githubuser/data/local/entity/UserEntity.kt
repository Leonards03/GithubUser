package com.dicoding.bfaa.githubuser.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "bio") val bio: String,
    @ColumnInfo(name = "avatar") val avatar: String,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "company") val company: String,
    @ColumnInfo(name = "followers_count") val followersCount: Int,
    @ColumnInfo(name = "following_count") val followingCount: Int
)