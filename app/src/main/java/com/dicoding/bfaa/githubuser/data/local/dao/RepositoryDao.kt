package com.dicoding.bfaa.githubuser.data.local.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dicoding.bfaa.githubuser.data.local.GithubDatabase.Companion.REPOSITORY_TABLE
import com.dicoding.bfaa.githubuser.data.local.entity.RepositoryEntity

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM $REPOSITORY_TABLE WHERE owner = :owner")
    suspend fun getRepositories(owner: String): List<RepositoryEntity>

    @Query("SELECT * FROM $REPOSITORY_TABLE WHERE owner = :owner")
    fun getRepositoriesCursor(owner: String): Cursor

    @Insert
    suspend fun insertRepositories(repositories: List<RepositoryEntity>)
}