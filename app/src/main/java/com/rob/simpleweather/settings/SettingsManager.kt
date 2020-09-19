package com.rob.simpleweather.settings

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class SettingsManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun shouldEnableAutomaticUpdates(): Boolean =
        sharedPreferences.getBoolean(UPDATE_KEY, false)

    fun updateAutomaticUpdateSwitch(value: Boolean) {
        sharedPreferences.edit {
            putBoolean(UPDATE_KEY, value)
        }
    }

    companion object {
        private const val UPDATE_KEY = "automatic_updates"
    }

}
