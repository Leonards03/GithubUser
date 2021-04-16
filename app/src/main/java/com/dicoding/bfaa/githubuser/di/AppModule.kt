package com.dicoding.bfaa.githubuser.di

import com.dicoding.bfaa.githubuser.data.local.LocalDataSource
import com.dicoding.bfaa.githubuser.data.mapper.RepositoryMapper
import com.dicoding.bfaa.githubuser.data.mapper.UserMapper
import com.dicoding.bfaa.githubuser.data.network.RemoteDataSource
import com.dicoding.bfaa.githubuser.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideMainRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        userMapper: UserMapper,
        repositoryMapper: RepositoryMapper
    ): MainRepository {
        return MainRepository(remoteDataSource, localDataSource, userMapper, repositoryMapper)
    }

    @Singleton
    @Provides
    fun provideUserMapper() = UserMapper()

    @Singleton
    @Provides
    fun provideRepositoryMapper() = RepositoryMapper()
}