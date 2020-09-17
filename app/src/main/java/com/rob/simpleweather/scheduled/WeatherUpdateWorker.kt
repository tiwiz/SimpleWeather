package com.rob.simpleweather.scheduled

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rob.simpleweather.favorites.FavoritesManager
import com.rob.simpleweather.repository.Repository
import kotlinx.coroutines.flow.collect

class WeatherUpdateWorker @WorkerInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val repository: Repository,
    private val favoritesManager: FavoritesManager
) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            favoritesManager.getFavoriteCities().collect { cities ->
                cities.forEach { city ->
                    repository.fetchWeatherForCity(city)
                }
            }
            Result.success()
        } catch(e: Exception) {
            Result.retry()
        }
    }
}
