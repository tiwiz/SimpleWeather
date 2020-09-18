package com.rob.simpleweather.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.rob.simpleweather.databinding.ActivityMainBinding
import com.rob.simpleweather.favorites.FavoritesViewModel
import com.rob.simpleweather.geolocation.UserLocationProvider
import com.rob.simpleweather.main.favorites.FavoritesAdapter
import com.rob.simpleweather.main.favorites.OnFavoriteClicked
import com.rob.simpleweather.main.forecast.ForecastAdapter
import com.rob.simpleweather.model.ForecastResponse
import com.rob.simpleweather.repository.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnFavoriteClicked {

    @Inject
    lateinit var userLocation: UserLocationProvider

    @Inject
    lateinit var favoritesAdapter: FavoritesAdapter

    @Inject
    lateinit var forecastAdapter: ForecastAdapter

    private val forecastViewModel by viewModels<ForecastViewModel>()
    private val favoritesViewModel by viewModels<FavoritesViewModel>()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        wireUi()

        loadWeatherFromUserLocation()
        loadFavorites()
    }

    private fun wireUi() {

        with(binding.listForecast) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = forecastAdapter
        }

        forecastViewModel.forecast.observe(this) { forecast ->
            forecast.doOnData { bindForecast(it) }
        }

        with(binding.listFavorite) {
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            adapter = favoritesAdapter
        }
    }

    private fun bindForecast(response: ForecastResponse) {
        with(binding) {
            txtCity.text = response.location.name
            val country = "${response.location.region}, ${response.location.country}"
            txtCountry.text = country
            txtWeatherType.text = response.current.condition.text
            val temperature = "${response.current.feelslike_c} ° C"
            txtTemperature.text = temperature
            iconWeather.load(response.current.condition.icon.extractIconUrl())

            forecastAdapter.updateForecast(response.forecast.forecastday)
        }
    }

    private fun loadWeatherFromUserLocation() {
        if (userLocation.canRequestLocation()) {

            userLocation.userLocation.observe(this) { city ->
                if (city != null) {
                    forecastViewModel.requestForecastFor(city)
                }
            }

            userLocation.requestUserLocation()
        }
    }

    private fun loadFavorites() {
        favoritesViewModel.favorites.observe(this) {favorites ->
            favorites.doOnData { cities ->
                favoritesAdapter.updateFavorites(cities)
            }
        }
    }

    override fun onFavoriteSelected(city: String) {
        forecastViewModel.requestForecastFor(city)
    }

    override fun onAddNewFavorite() {
        //TODO open favorite screen
    }
}
