package com.dicoding.bfaa.githubuser.data.local.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.dicoding.bfaa.githubuser.data.local.GithubDatabase.Companion.AUTHORITY
import com.dicoding.bfaa.githubuser.data.local.GithubDatabase.Companion.CONTENT_URI
import com.dicoding.bfaa.githubuser.data.local.GithubDatabase.Companion.FOLLOWERS_TABLE
import com.dicoding.bfaa.githubuser.data.local.GithubDatabase.Companion.FOLLOWING_TABLE
import com.dicoding.bfaa.githubuser.data.local.GithubDatabase.Companion.REPOSITORY_TABLE
import com.dicoding.bfaa.githubuser.data.local.GithubDatabase.Companion.USERS_TABLE
import com.dicoding.bfaa.githubuser.data.repository.MainRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class FavoriteUserContentProvider @Inject constructor() : ContentProvider() {
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface ContentProviderEntryPoint {
        fun repository(): MainRepository
    }

    private lateinit var repository: MainRepository

    override fun onCreate(): Boolean {
        try {
            val appContext = context?.applicationContext ?: throw IllegalStateException()
            val hiltEntryPoint =
                EntryPointAccessors.fromApplication(
                    appContext,
                    ContentProviderEntryPoint::class.java
                )
            repository = hiltEntryPoint.repository()
            Log.d(TAG, "Content Provider Created ")
        } catch (exception: Exception) {
            Log.e(TAG, exception.message ?: "Error occured $exception")
        }
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val username = uri.lastPathSegment ?: String()
        Log.d("ContentProvider", "Query function start, $username, $uri")
        return when (uriMatcher.match(uri)) {
            USER -> repository.getFavoriteCursor()
            USER_ID -> repository.getUserCursor(username)
            FOLLOWERS -> repository.getFollowersCursor(username)
            FOLLOWING -> repository.getFollowingCursor(username)
            REPOSITORY -> repository.getRepositoriesCursor(username)
            else -> {
                Log.e("ContentProvider", "No matches")
                null
            }
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val username = uri.lastPathSegment ?: String()
        val deleted: Int = when(USER_ID){
            uriMatcher.match(uri) -> repository.removeUserFromFavorite(username)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return deleted
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int = -1

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun getType(uri: Uri): String? = null

    companion object {
        private const val USER = 1
        private const val USER_ID = 2
        private const val FOLLOWERS = 3
        private const val FOLLOWING = 4
        private const val REPOSITORY = 5
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.apply {
                // content://com.dicoding.bfaa.githubuser/users
                addURI(AUTHORITY, USERS_TABLE, USER)

                // content://com.dicoding.bfaa.githubuser/users/username
                addURI(AUTHORITY, "$USERS_TABLE/*", USER_ID)

                // content://com.dicoding.bfaa.githubuser/followers/username
                addURI(AUTHORITY, "$FOLLOWERS_TABLE/*", FOLLOWERS)

                // content://com.dicoding.bfaa.githubuser/following/username
                addURI(AUTHORITY, "$FOLLOWING_TABLE/*", FOLLOWING)

                // content://com.dicoding.bfaa.githubuser/repositories/username
                addURI(AUTHORITY, "$REPOSITORY_TABLE/*", REPOSITORY)
            }
        }

        private val TAG = FavoriteUserContentProvider::class.simpleName
    }
}