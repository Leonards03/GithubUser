package com.dicoding.bfaa.githubuser.data.local

import android.net.Uri
import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.bfaa.githubuser.data.local.dao.RepositoryDao
import com.dicoding.bfaa.githubuser.data.local.dao.UserDao
import com.dicoding.bfaa.githubuser.data.local.entity.FollowersEntity
import com.dicoding.bfaa.githubuser.data.local.entity.FollowingEntity
import com.dicoding.bfaa.githubuser.data.local.entity.RepositoryEntity
import com.dicoding.bfaa.githubuser.data.local.entity.UserEntity

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
    abstract fun repoDao(): RepositoryDao

    companion object {
        const val AUTHORITY = "com.dicoding.bfaa.githubuser"
        private const val SCHEME = "content"
        const val USERS_TABLE = "users"
        const val FOLLOWERS_TABLE = "followers"
        const val FOLLOWING_TABLE = "following"
        const val REPOSITORY_TABLE = "repositories"


        val CONTENT_URI: Uri = Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(USERS_TABLE)
            .build()
    }
}