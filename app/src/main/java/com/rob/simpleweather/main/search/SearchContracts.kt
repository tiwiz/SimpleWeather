package com.rob.simpleweather.main.search

import androidx.recyclerview.widget.RecyclerView
import com.rob.simpleweather.databinding.CityItemBinding
import com.rob.simpleweather.model.SearchEntry

class SearchViewHolder(
    private val binding: CityItemBinding,
    private val callbacks: SearchCallbacks?
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(entry: SearchEntry) {
        val city = entry.name.split(",").first()

        binding.txtCityName.text = city
        binding.txtCompleteName.text = entry.name

        val country = "${entry.region}, ${entry.country}"
        binding.txtCountryCity.text = country

        binding.item.setOnClickListener {
            callbacks?.onCitySaved(city)
        }
    }
}

interface SearchCallbacks {

    fun onCitySaved(city: String)
}
