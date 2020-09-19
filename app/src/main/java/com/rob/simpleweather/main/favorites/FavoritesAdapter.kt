package com.rob.simpleweather.main.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rob.simpleweather.databinding.FavoriteElementBinding
import javax.inject.Inject

class FavoritesAdapter @Inject constructor(
    private val layoutInflater: LayoutInflater,
    private val callbacks: OnFavoriteClicked?
) : RecyclerView.Adapter<FavoritesViewHolder>() {

    private val items : ArrayList<String?> = arrayListOf(null)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder =
        FavoritesViewHolder(
            FavoriteElementBinding.inflate(layoutInflater, parent, false),
            callbacks
        )

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bindTo(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateFavorites(cities: List<String>) {
        items.clear()
        items.addAll(cities)
        items.add(null)

        notifyDataSetChanged()
    }
}
