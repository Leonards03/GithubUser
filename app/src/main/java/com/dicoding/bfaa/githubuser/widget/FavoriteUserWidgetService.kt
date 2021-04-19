package com.dicoding.bfaa.githubuser.widget

import android.content.Intent
import android.widget.RemoteViewsService

class FavoriteUserWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
        FavoriteUserRemoteViewsFactory(this.applicationContext)
}