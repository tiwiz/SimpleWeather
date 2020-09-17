package com.rob.simpleweather.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.rob.simpleweather.databinding.ActivityMainBinding
import com.rob.simpleweather.favorites.FavoritesViewModel
import com.rob.simpleweather.geolocation.UserLocationProvider
import com.rob.simpleweather.repository.CitySearchViewModel
import com.rob.simpleweather.repository.ForecastViewModel
import com.rob.simpleweather.scheduled.WeatherUpdater
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var userLocationProvider: UserLocationProvider

    @Inject
    lateinit var weatherUpdater: WeatherUpdater

    private val forecastViewModel by viewModels<ForecastViewModel>()
    private val searchViewModel by viewModels<CitySearchViewModel>()
    private val favoritesViewModel by viewModels<FavoritesViewModel>()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnForecastRequest.setOnClickListener {
            forecastViewModel.requestForecastFor("Fossano")
        }

        binding.btnCitySearch.setOnClickListener {
            searchViewModel.searchCities("Fossano")
        }

        favoritesViewModel.favorites.observe(this) {
            it.doOnData { cities ->
                binding.txtCityOutput.text = cities.joinToString()
            }
        }

        binding.btnAddCity.setOnClickListener {
            favoritesViewModel.markCityAsFavorite(binding.editCityToAdd.text.toString(), true)
        }

        binding.btnRemoveCity.setOnClickListener {
            favoritesViewModel.markCityAsFavorite(binding.editCityToAdd.text.toString(), false)
        }

        binding.btnRequestLocation.isEnabled = userLocationProvider.canRequestLocation()

        userLocationProvider.userLocation.observe(this) { city ->
            if (city != null) {
                binding.txtUserLocation.text = "Last user location: $city"
            }
        }

        binding.btnRequestLocation.setOnClickListener {
            userLocationProvider.requestUserLocation()
        }

        binding.btnScheduleUpdate.setOnClickListener {
            weatherUpdater.scheduleRecurringUpdate()
        }
    }
}
