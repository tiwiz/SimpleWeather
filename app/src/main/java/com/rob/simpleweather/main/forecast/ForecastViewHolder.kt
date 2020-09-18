package com.rob.simpleweather.main.forecast

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rob.simpleweather.databinding.ForecastNextDaysBinding
import com.rob.simpleweather.main.extractIconUrl
import com.rob.simpleweather.model.ForecastDay

class ForecastViewHolder(private val binding: ForecastNextDaysBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(forecast: ForecastDay) {
        binding.txtDate.text = forecast.date

        val temperature = "${forecast.day.condition.text}° C"
        binding.txtAvgTemp.text = temperature

        binding.iconForecastWeather.load(
            forecast.day.condition.icon.extractIconUrl()
        )
    }
}
