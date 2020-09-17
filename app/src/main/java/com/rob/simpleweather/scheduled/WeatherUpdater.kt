package com.rob.simpleweather.scheduled

import android.app.Application
import androidx.work.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherUpdater @Inject constructor(app: Application){

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.UNMETERED)
        .setRequiresDeviceIdle(true)
        .build()

    private val request  =
        PeriodicWorkRequestBuilder<WeatherUpdateWorker>(1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

    private val workManager by lazy {
        WorkManager.getInstance(app)
    }

    fun scheduleRecurringUpdate() {
        workManager.enqueueUniquePeriodicWork("weatherUpdate",
            ExistingPeriodicWorkPolicy.KEEP,
            request)
    }
}
