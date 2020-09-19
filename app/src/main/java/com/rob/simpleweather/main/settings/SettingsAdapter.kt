package com.rob.simpleweather.main.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rob.simpleweather.databinding.CitySettingsItemBinding
import javax.inject.Inject
import javax.inject.Singleton

class SettingsAdapter @Inject constructor(
    private val layoutInflater: LayoutInflater,
    private val callback: OnItemRemovedCallback?
) : RecyclerView.Adapter<CitySettingsViewHolder>() {

    private val items = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitySettingsViewHolder =
        CitySettingsViewHolder(
            CitySettingsItemBinding.inflate(layoutInflater, parent, false)
        )

    override fun onBindViewHolder(holder: CitySettingsViewHolder, position: Int) {
        holder.bindTo(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<String>) {
        items.clear()
        items.addAll(newItems)

        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        val removedItem = items.removeAt(position)
        notifyItemRemoved(position)

        callback?.onItemRemoved(removedItem)
    }
}
