package com.dicoding.bfaa.consumerapp.data.local

import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.dicoding.bfaa.consumerapp.data.local.provider.ContentProviderConstants.FOLLOWERS
import com.dicoding.bfaa.consumerapp.data.local.provider.ContentProviderConstants.FOLLOWING
import com.dicoding.bfaa.consumerapp.data.local.provider.ContentProviderConstants.REPOSITORY
import com.dicoding.bfaa.consumerapp.data.local.provider.ContentProviderConstants.USER
import com.dicoding.bfaa.consumerapp.data.local.provider.ContentProviderConstants.USER_ID
import com.dicoding.bfaa.consumerapp.data.local.provider.ContentProviderConstants.getContentURI

class LocalDataSource {

    fun getFavoriteUsers(context: Context): Cursor? {
        val uri = getContentURI(USER)
        return query(context, uri)
    }

    fun getUser(username: String, context: Context): Cursor? {
        val uri = getContentURI(USER_ID, username)
        return query(context, uri)
    }

    fun getFollowers(username: String, context: Context): Cursor? {
        val uri = getContentURI(FOLLOWERS, username)
        return query(context, uri)
    }

    fun getFollowing(username: String, context: Context): Cursor? {
        val uri = getContentURI(FOLLOWING, username)
        return query(context, uri)
    }

    fun getRepositories(username: String, context: Context): Cursor? {
        val uri = getContentURI(REPOSITORY, username)
        return query(context, uri)
    }

    fun removeUserFromFavorite(username: String, context: Context){
        val uri = getContentURI(USER_ID, username)
        context.contentResolver.delete(uri,null,null)
    }

    private fun query(context: Context, uri: Uri): Cursor? {
        return context.contentResolver
            .query(uri, null, null, null, null)
    }

}