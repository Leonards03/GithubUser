package com.dicoding.bfaa.githubuser.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Binder
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.dicoding.bfaa.githubuser.R
import com.dicoding.bfaa.githubuser.data.local.GithubDatabase.Companion.CONTENT_URI
import com.dicoding.bfaa.githubuser.data.mapper.UserMapper
import com.dicoding.bfaa.githubuser.data.model.User
import com.dicoding.bfaa.githubuser.view.ui.detail.DetailActivity
import com.dicoding.bfaa.githubuser.view.ui.detail.DetailActivity.Companion.EXTRA_USERNAME

class FavoriteUserRemoteViewsFactory(
    private val context: Context
) : RemoteViewsService.RemoteViewsFactory {

    private val userMapper: UserMapper = UserMapper()

    private var userList: ArrayList<User> = ArrayList()
    private var cursor: Cursor? = null

    override fun onCreate() {}

    override fun onDataSetChanged() {
        Log.d(FavoriteUserRemoteViewsFactory::class.simpleName, "onDataSetChanged")
        try {
            cursor?.close()

            val identityToken = Binder.clearCallingIdentity()

            cursor = context.contentResolver.query(CONTENT_URI, null, null, null, null)
            cursor?.let {
                userList.clear()
                userList.addAll(userMapper.mapToModels(cursor = it))
            }

            Binder.restoreCallingIdentity(identityToken)
        } catch (exception: Exception) {
            Log.e(TAG, exception.message ?: exception.toString())
        } finally {
            Log.d(TAG, "onDataSetChanged executed successfully")
        }
    }

    override fun onDestroy() {
        cursor = null
    }

    override fun getViewAt(position: Int): RemoteViews {
        val views = RemoteViews(context.packageName, R.layout.user_widget_layout)

        if (!userList.isNullOrEmpty()) {
            views.apply {
                userList[position].let { user ->
                    setImageViewBitmap(
                        R.id.img_photo,
                        Glide.with(context)
                            .asBitmap()
                            .load(user.avatar)
                            .submit()
                            .get()
                    )
                    Intent().apply {
                        putExtra(EXTRA_USERNAME, user.username)
                        putExtra(DetailActivity.IS_FAVORITE, true)
                        views.setOnClickFillInIntent(R.id.item_view, this)
                    }
                }
            }

        }
        return views
    }

    override fun getCount(): Int = userList.size

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false

    companion object {
        private val TAG = FavoriteUserRemoteViewsFactory::class.simpleName
    }
}