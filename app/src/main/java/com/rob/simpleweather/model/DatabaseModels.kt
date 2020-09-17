package com.rob.simpleweather.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDateTime

@Entity
data class ForecastTable(
    @PrimaryKey val city: String,
    val forecastResponse: ForecastResponse,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
