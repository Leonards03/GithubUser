package com.dicoding.bfaa.githubuser.data.network

import com.dicoding.bfaa.githubuser.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RemoteInterceptor : Interceptor {
    private val PERSONAL_TOKEN = BuildConfig.GITHUB_PERSONAL_TOKEN
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("Authorization", PERSONAL_TOKEN)
            .build()

        return chain.proceed(newRequest)
    }
}