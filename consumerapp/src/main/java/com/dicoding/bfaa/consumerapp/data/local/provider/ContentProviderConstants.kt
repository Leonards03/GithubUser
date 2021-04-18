package com.dicoding.bfaa.consumerapp.data.local.provider

import android.content.UriMatcher
import android.net.Uri

object ContentProviderConstants {
    private const val AUTHORITY = "com.dicoding.bfaa.githubuser"
    private const val SCHEME = "content"
    private const val USERS_TABLE = "users"
    private const val FOLLOWERS_TABLE = "followers"
    private const val FOLLOWING_TABLE = "following"
    private const val REPOSITORY_TABLE = "repositories"

    const val USER = 1
    const val USER_ID = 2
    const val FOLLOWERS = 3
    const val FOLLOWING = 4
    const val REPOSITORY = 5

    val CONTENT_URI: Uri = uriBuilder().build()

    private fun uriBuilder() = Uri.Builder()
        .scheme(SCHEME)
        .authority(AUTHORITY)

    fun getContentURI(type: Int, username: String? = null): Uri {
        return when (type) {
            USER -> uriBuilder()
                .appendPath(USERS_TABLE)
                .build()

            USER_ID -> uriBuilder()
                .appendPath(USERS_TABLE)
                .appendPath(username)
                .build()

            FOLLOWERS -> uriBuilder()
                .appendPath(FOLLOWERS_TABLE)
                .appendPath(username)
                .build()

            FOLLOWING -> uriBuilder()
                .appendPath(FOLLOWING_TABLE)
                .appendPath(username)
                .build()

            else -> uriBuilder()
                .appendPath(REPOSITORY_TABLE)
                .appendPath(username)
                .build()
        }
    }
}