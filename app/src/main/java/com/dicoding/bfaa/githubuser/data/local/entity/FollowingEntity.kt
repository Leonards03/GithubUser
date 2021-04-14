package com.dicoding.bfaa.githubuser.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "following")
data class FollowingEntity(
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "avatar") val avatar: String
)