package com.dicoding.bfaa.consumerapp.di

import com.dicoding.bfaa.consumerapp.data.local.LocalDataSource
import com.dicoding.bfaa.consumerapp.data.mapper.RepositoryMapper
import com.dicoding.bfaa.consumerapp.data.mapper.UserMapper
import com.dicoding.bfaa.consumerapp.data.repository.MainRepository
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
        localDataSource: LocalDataSource,
        userMapper: UserMapper,
        repositoryMapper: RepositoryMapper
    ): MainRepository {
        return MainRepository(localDataSource, userMapper, repositoryMapper)
    }

    @Singleton
    @Provides
    fun provideUserMapper(): UserMapper = UserMapper()

    @Singleton
    @Provides
    fun provideRepositoryMapper(): RepositoryMapper = RepositoryMapper()
}