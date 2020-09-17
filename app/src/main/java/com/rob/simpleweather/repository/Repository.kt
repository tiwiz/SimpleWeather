package com.rob.simpleweather.repository

import javax.inject.Inject

class Repository @Inject constructor(private val weatherApi: WeatherApi) {

    suspend fun fetchWeatherForCity(city: String) =
        weatherApi.fetchForecastFor(city)
}
