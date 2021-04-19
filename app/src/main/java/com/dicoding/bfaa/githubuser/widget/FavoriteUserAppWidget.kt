package com.dicoding.bfaa.githubuser.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.Intent.URI_INTENT_SCHEME
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.net.toUri
import com.dicoding.bfaa.githubuser.R
import com.dicoding.bfaa.githubuser.view.ui.detail.DetailActivity

class FavoriteUserAppWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)
        intent?.action?.let { action ->
            if (action == CLICK_ACTION) {
                val username = intent.getStringExtra(DetailActivity.EXTRA_USERNAME)
                Toast.makeText(context, username, Toast.LENGTH_SHORT).show()
            } else if (action == REFRESH_ACTION) {
                val manager = AppWidgetManager.getInstance(context)
                val widgets = ComponentName(context, FavoriteUserAppWidget::class.java)
                manager.notifyAppWidgetViewDataChanged(
                    manager.getAppWidgetIds(widgets),
                    R.id.stack_view
                )
            }
        }
    }

    companion object {
        private const val BASE_PATH = "com.dicoding.bfaa.githubuser"
        const val REFRESH_ACTION = "$BASE_PATH.REFRESH"
        const val CLICK_ACTION = "$BASE_PATH.CLICK"
        private fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            // create intent to trigger the services
            val serviceIntent = Intent(context, FavoriteUserWidgetService::class.java).apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                data = toUri(URI_INTENT_SCHEME).toUri()
            }

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.favorite_user_app_widget)
            views.apply {
                setRemoteAdapter(R.id.stack_view, serviceIntent)
            }


            val clickIntent = Intent(context, FavoriteUserAppWidget::class.java).apply {
                action = CLICK_ACTION
                data = toUri(URI_INTENT_SCHEME).toUri()
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }

            val clickPendingIntent = PendingIntent.getBroadcast(
                context,
                99,
                clickIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            views.setPendingIntentTemplate(R.id.stack_view, clickPendingIntent)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}