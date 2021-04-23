package com.dicoding.bfaa.githubuser.di

import com.dicoding.bfaa.githubuser.BuildConfig
import com.dicoding.bfaa.githubuser.data.network.GithubServices
import com.dicoding.bfaa.githubuser.data.network.RemoteDataSource
import com.dicoding.bfaa.githubuser.data.network.RemoteInterceptor
import com.dicoding.bfaa.githubuser.utils.Constants.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(RemoteInterceptor())
            .retryOnConnectionFailure(false)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            })
            .build()


    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
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