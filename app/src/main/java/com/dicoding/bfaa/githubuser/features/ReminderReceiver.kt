package com.dicoding.bfaa.githubuser.features

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.dicoding.bfaa.githubuser.R
import com.dicoding.bfaa.githubuser.view.ui.home.HomeActivity
import java.util.*

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra(EXTRA_TITLE)!!
        val message = intent.getStringExtra(EXTRA_MESSAGE)!!
        showNotification(context, title, message)
    }

    fun setDailyReminder(context: Context, title: String, message: String) {
        val alarmManager = context.getSystemService<AlarmManager>()

        // set up intent
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(EXTRA_TITLE, title)
            putExtra(EXTRA_MESSAGE, message)
        }

        // set up time for fires intent on 9 am
        val calendar = Calendar.getInstance()
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        // create Pending intent and set repeating fires intent
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                TYPE_DAILY_REMINDER,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        alarmManager?.setRepeating(
            AlarmManager.RTC,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun cancelDailyReminder(context: Context) {
        val alarmManager = context.getSystemService<AlarmManager>()
        val intent = Intent(context, ReminderReceiver::class.java)

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                TYPE_DAILY_REMINDER,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        pendingIntent.cancel()

        alarmManager?.cancel(pendingIntent)
    }

    private fun showNotification(context: Context, title: String, message: String) {

        val channelId = "channel_0"
        val channelName = "DailyReminder channel"

        val notificationIntent = Intent(context, HomeActivity::class.java)

        val pendingIntent = TaskStackBuilder.create(context)
            .addNextIntent(notificationIntent)
            .getPendingIntent(NOTIFICATION_INTENT_ID, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManager = context.getSystemService<NotificationManager>()
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_github)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)

            with(channel) {
                enableVibration(true)
                vibrationPattern = longArrayOf(1000, 1000)
            }

            builder.setChannelId(channelId)

            notificationManager?.createNotificationChannel(channel)
        }

        val notification = builder.build()

        notificationManager?.notify(NOTIF_ID, notification)
    }

    companion object {
        private const val TYPE_DAILY_REMINDER = 100
        private const val NOTIF_ID = 300
        private const val NOTIFICATION_INTENT_ID = 200
        const val EXTRA_TITLE = "title"
        const val EXTRA_MESSAGE = "message"
    }
}