package com.dicoding.bfaa.githubuser.view.ui.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuInflater
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.dicoding.bfaa.githubuser.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val localPreference: Preference? = findPreference(getString(R.string.key_lang))
        localPreference?.intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.search)?.isVisible = false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.search).isVisible = false
    }
}