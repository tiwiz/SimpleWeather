package com.rob.simpleweather.repository

import com.rob.simpleweather.model.ForecastResponse
import com.rob.simpleweather.model.ForecastTable
import org.threeten.bp.LocalDateTime
import timber.log.Timber
import javax.inject.Inject

class Repository @Inject constructor(
    private val weatherApi: WeatherApi,
    private val forecastDao: ForecastDao
) {

    suspend fun fetchWeatherForCity(city: String) : ForecastResponse {
        val dbResult = forecastDao.findByCity(city)
        val threshold = LocalDateTime.now().minusSeconds(90)

        return if (dbResult == null || dbResult.timestamp.isBefore(threshold)) {
            Timber.i("Returning result from network")
            weatherApi.fetchForecastFor(city).apply {
                val table = ForecastTable(
                    city = city,
                    forecastResponse = this
                )
                Timber.i("Saving result to database")
                forecastDao.updateCityWeather(table)
            }
        } else {
            Timber.i("Returning result from DB")
            dbResult.forecastResponse
        }
    }

}
