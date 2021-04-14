package com.dicoding.bfaa.githubuser.features

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.getSystemService
import java.util.*

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra(EXTRA_TITLE)!!
        val message = intent.getStringExtra(EXTRA_MESSAGE)!!
        showNotification(context, title, message)
    }

    fun setDailyReminder(context: Context, title: String, message: String){
        val alarmManager = context.getSystemService<AlarmManager>()

        // set up intent
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(EXTRA_TITLE, title)
            putExtra(EXTRA_MESSAGE, message)
        }

        // set up time for fires intent on 9 am
        val calendar = Calendar.getInstance()
        with(calendar){
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        // create Pending intent and set repeating fires intent
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager?.setRepeating(AlarmManager.RTC, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    fun cancelDailyReminder(context: Context){
        val alarmManager = context.getSystemService<AlarmManager>()
        val intent = Intent(context, ReminderReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        pendingIntent.cancel()

        alarmManager?.cancel(pendingIntent)
    }

    private fun showNotification(context: Context, title: String, message: String){
        val notificationManager = context.getSystemService<NotificationManager>()


    }

    companion object {
        const val TYPE_DAILY_REMINDER ="daily_reminder"
        const val EXTRA_TITLE = "title"
        const val EXTRA_MESSAGE = "message"
    }
}