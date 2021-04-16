package com.dicoding.bfaa.githubuser.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dicoding.bfaa.githubuser.data.local.entity.RepositoryEntity

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM repositories WHERE owner = :owner")
    suspend fun getRepositories(owner: String): List<RepositoryEntity>

    @Insert
    suspend fun insertRepositories(repositories: List<RepositoryEntity>)
}