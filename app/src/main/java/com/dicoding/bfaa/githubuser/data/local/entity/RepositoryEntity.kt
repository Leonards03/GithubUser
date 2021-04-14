package com.dicoding.bfaa.githubuser.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "repositories")
data class RepositoryEntity(
    @ColumnInfo val owner: String,
    @ColumnInfo val name: String,
    @ColumnInfo val url: String,
    @ColumnInfo val language: String
)