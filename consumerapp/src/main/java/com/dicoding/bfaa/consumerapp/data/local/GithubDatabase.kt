package com.dicoding.bfaa.consumerapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.bfaa.consumerapp.data.local.dao.RepositoryDao
import com.dicoding.bfaa.consumerapp.data.local.dao.UserDao
import com.dicoding.bfaa.consumerapp.data.local.entity.FollowersEntity
import com.dicoding.bfaa.consumerapp.data.local.entity.FollowingEntity
import com.dicoding.bfaa.consumerapp.data.local.entity.RepositoryEntity
import com.dicoding.bfaa.consumerapp.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        RepositoryEntity::class,
        FollowersEntity::class,
        FollowingEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun repositoryDao(): RepositoryDao
}