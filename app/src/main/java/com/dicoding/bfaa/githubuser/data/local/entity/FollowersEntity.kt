package com.dicoding.bfaa.githubuser.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "followers")
data class FollowersEntity(
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "avatar") val avatar: String
)