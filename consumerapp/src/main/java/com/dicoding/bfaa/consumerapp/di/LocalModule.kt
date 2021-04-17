package com.dicoding.bfaa.consumerapp.di

import android.app.Application
import androidx.room.Room
import com.dicoding.bfaa.consumerapp.data.local.GithubDatabase
import com.dicoding.bfaa.consumerapp.data.local.LocalDataSource
import com.dicoding.bfaa.consumerapp.data.local.dao.RepositoryDao
import com.dicoding.bfaa.consumerapp.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Singleton
    @Provides
    fun provideLocalDataSource(userDao: UserDao, repositoryDao: RepositoryDao): LocalDataSource {
        return LocalDataSource(userDao, repositoryDao)
    }

    @Singleton
    @Provides
    fun provideDatabase(application: Application): GithubDatabase {
        return Room
            .databaseBuilder(application, GithubDatabase::class.java, "github.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(database: GithubDatabase): UserDao = database.userDao()

    @Singleton
    @Provides
    fun provideRepositoryDao(database: GithubDatabase): RepositoryDao = database.repositoryDao()
}