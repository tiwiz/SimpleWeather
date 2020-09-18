package com.rob.simpleweather.main.forecast

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rob.simpleweather.databinding.ForecastNextDaysBinding
import com.rob.simpleweather.main.extractIconUrl
import com.rob.simpleweather.model.ForecastDay
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter

class ForecastViewHolder(private val binding: ForecastNextDaysBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    fun bindTo(forecast: ForecastDay) {
        val timestamp = LocalDateTime.ofEpochSecond(
            forecast.date_epoch.toLong(),
            0,
            ZoneOffset.UTC
        ).format(formatter)

        binding.txtDate.text = timestamp

        val temperature = "${forecast.day.avgtemp_c}° C"
        binding.txtAvgTemp.text = temperature

        binding.txtForecastWeatherType.text = forecast.day.condition.text

        binding.iconForecastWeather.load(
            forecast.day.condition.icon.extractIconUrl()
        )
    }
}
