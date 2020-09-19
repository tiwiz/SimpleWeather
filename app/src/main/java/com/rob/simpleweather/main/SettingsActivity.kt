package com.rob.simpleweather.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rob.simpleweather.databinding.ActivitySettingsBinding
import com.rob.simpleweather.scheduled.WeatherUpdater
import com.rob.simpleweather.settings.SettingsManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    @Inject
    lateinit var settingsManager: SettingsManager

    @Inject
    lateinit var weatherUpdater: WeatherUpdater

    private val binding by lazy {
        ActivitySettingsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.switchAutomaticUpdate.isActivated =
            settingsManager.shouldEnableAutomaticUpdates()

        binding.switchAutomaticUpdate.setOnCheckedChangeListener { _, value ->
            settingsManager.updateAutomaticUpdateSwitch(value)
            if (value) {
                weatherUpdater.scheduleRecurringUpdate()
            } else {
                weatherUpdater.cancelRecurringUpdate()
            }
        }
    }
}
