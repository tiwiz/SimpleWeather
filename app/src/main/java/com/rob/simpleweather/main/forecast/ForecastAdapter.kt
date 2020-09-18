package com.rob.simpleweather.main.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rob.simpleweather.databinding.ForecastNextDaysBinding
import com.rob.simpleweather.model.ForecastDay
import javax.inject.Inject

class ForecastAdapter @Inject constructor(
    private val layoutInflater: LayoutInflater,
) : RecyclerView.Adapter<ForecastViewHolder>() {

    private val items = arrayListOf<ForecastDay>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder =
        ForecastViewHolder(
            ForecastNextDaysBinding.inflate(layoutInflater, parent, false)
        )

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bindTo(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateForecast(days: List<ForecastDay>) {
        items.clear()
        items.addAll(days)

        notifyDataSetChanged()
    }
}
