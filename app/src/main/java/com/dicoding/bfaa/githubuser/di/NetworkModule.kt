package com.dicoding.bfaa.githubuser.di

import com.dicoding.bfaa.githubuser.data.network.GithubServices
import com.dicoding.bfaa.githubuser.data.network.RemoteDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideGithubServices(retrofit: Retrofit.Builder): GithubServices {
        return retrofit.build()
            .create(GithubServices::class.java)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        retrofit: GithubServices
    ): RemoteDataSource {
        return RemoteDataSource(retrofit)
    }
}