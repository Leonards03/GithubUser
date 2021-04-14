package com.dicoding.bfaa.githubuser.view.ui.settings

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.dicoding.bfaa.githubuser.R

class SettingsFragment : PreferenceFragmentCompat() {

    private var reminderPreference: SwitchPreferenceCompat? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val localPreference: Preference? = findPreference(getString(R.string.key_lang))
        localPreference?.intent = Intent(Settings.ACTION_LOCALE_SETTINGS)

        reminderPreference = findPreference(getString(R.string.key_reminder))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
            val isReminderOn = prefs.getBoolean(REMINDER, false)
//            reminderPreference.switchTextOn
        }
    }

    companion object {
        private const val REMINDER = "reminder"
    }

}