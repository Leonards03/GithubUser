package com.dicoding.bfaa.githubuser.view.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.dicoding.bfaa.githubuser.R
import com.dicoding.bfaa.githubuser.databinding.ActivityHomeBinding
import com.dicoding.bfaa.githubuser.features.ReminderReceiver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var sharedPreference: SharedPreferences

    @Inject
    lateinit var reminderReceiver: ReminderReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GithubUser)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.appBar))

        with(binding) {
            val navController = findNavController(R.id.nav_host)
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.navigation_home, R.id.navigation_favorite, R.id.navigation_settings
                ), drawerLayout
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }

        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreference.registerOnSharedPreferenceChangeListener { prefs, key ->
            val isReminderActive = prefs.getBoolean(key, false)
            if (isReminderActive) {
                reminderReceiver.setDailyReminder(
                    this@HomeActivity,
                    getString(R.string.notification_title),
                    getString(R.string.notification_text)
                )
            } else {
                reminderReceiver.cancelDailyReminder(this@HomeActivity)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}