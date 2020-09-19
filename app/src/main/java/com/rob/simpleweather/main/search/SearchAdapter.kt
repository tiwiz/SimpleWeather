package com.rob.simpleweather.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rob.simpleweather.databinding.CityItemBinding
import com.rob.simpleweather.model.SearchEntry
import javax.inject.Inject

class SearchAdapter @Inject constructor(
    private val layoutInflater: LayoutInflater,
    private val callbacks: SearchCallbacks?
) : RecyclerView.Adapter<SearchViewHolder>() {

    private val items = arrayListOf<SearchEntry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(
            CityItemBinding.inflate(layoutInflater, parent, false),
            callbacks
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount() = items.size

    fun updateItems(entries: List<SearchEntry>) {
        items.clear()
        items.addAll(entries)

        notifyDataSetChanged()
    }
}
