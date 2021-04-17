package com.dicoding.bfaa.githubuser.data.local.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds.Note.NOTE
import com.dicoding.bfaa.githubuser.data.local.GithubDatabase.Companion.AUTHORITY
import com.dicoding.bfaa.githubuser.data.local.GithubDatabase.Companion.TABLE_NAME
import com.dicoding.bfaa.githubuser.data.repository.MainRepository
import javax.inject.Inject

class FavoriteUserContentProvider @Inject constructor(
    private val repository: MainRepository
) : ContentProvider() {

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when(uriMatcher.match(uri)){
            USER -> repository.getFavoriteCursor()
            USER_ID -> repository.getUserCursor(uri.lastPathSegment!!)
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }

    companion object {
        private const val USER = 1
        private const val USER_ID = 2
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            // content://com.dicoding.picodiploma.mynotesapp/note
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, USER)
            // content://com.dicoding.picodiploma.mynotesapp/note/id
            uriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", USER_ID)
        }

    }
}