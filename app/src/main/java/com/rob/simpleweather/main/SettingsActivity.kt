package com.rob.simpleweather.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.rob.simpleweather.databinding.ActivitySettingsBinding
import com.rob.simpleweather.favorites.FavoritesViewModel
import com.rob.simpleweather.main.settings.OnItemRemovedCallback
import com.rob.simpleweather.main.settings.SettingsAdapter
import com.rob.simpleweather.main.settings.SwipeToDeleteCallback
import com.rob.simpleweather.scheduled.WeatherUpdater
import com.rob.simpleweather.settings.SettingsManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SettingsActivity : AppCompatActivity(), OnItemRemovedCallback {

    @Inject
    lateinit var settingsManager: SettingsManager

    @Inject
    lateinit var weatherUpdater: WeatherUpdater

    @Inject
    lateinit var settingsAdapter: SettingsAdapter

    private val favoritesViewModel by viewModels<FavoritesViewModel>()

    private val binding by lazy {
        ActivitySettingsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.switchAutomaticUpdate.isChecked =
            settingsManager.shouldEnableAutomaticUpdates()

        binding.switchAutomaticUpdate.setOnCheckedChangeListener { _, value ->
            settingsManager.updateAutomaticUpdateSwitch(value)
            if (value) {
                weatherUpdater.scheduleRecurringUpdate()
            } else {
                weatherUpdater.cancelRecurringUpdate()
            }
        }

        with(binding.citySettingsList) {
            layoutManager = LinearLayoutManager(this@SettingsActivity)
            adapter = settingsAdapter

            ItemTouchHelper(
                SwipeToDeleteCallback(this@SettingsActivity, settingsAdapter)
            ).attachToRecyclerView(this)
        }

        favoritesViewModel.favorites.observe(this) {
            it.doOnData { cities -> settingsAdapter.updateItems(cities) }
        }
    }

    override fun onItemRemoved(city: String) {
        favoritesViewModel.markCityAsFavorite(city, false)
    }
}
