package com.dicoding.bfaa.githubuser.di

import com.dicoding.bfaa.githubuser.data.mapper.Mapper
import com.dicoding.bfaa.githubuser.data.mapper.RepositoryMapper
import com.dicoding.bfaa.githubuser.data.mapper.UserMapper
import com.dicoding.bfaa.githubuser.data.local.LocalDataSource
import com.dicoding.bfaa.githubuser.data.repository.MainRepository
import com.dicoding.bfaa.githubuser.data.network.RemoteDataSource
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
        mapper: Mapper
    ): MainRepository {
        return MainRepository(remoteDataSource, localDataSource, mapper)
    }

    @Singleton
    @Provides
    fun provideMapper(
        userMapper: UserMapper,
        repositoryMapper: RepositoryMapper
    ): Mapper {
        return Mapper(userMapper, repositoryMapper)
    }

    @Singleton
    @Provides
    fun provideUserMapper() = UserMapper()

    @Singleton
    @Provides
    fun provideRepositoryMapper() = RepositoryMapper()
}