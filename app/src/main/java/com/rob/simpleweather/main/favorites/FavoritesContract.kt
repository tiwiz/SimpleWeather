package com.rob.simpleweather.main.favorites

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rob.simpleweather.databinding.FavoriteElementBinding

class FavoritesViewHolder(
    private val binding: FavoriteElementBinding,
    private val callback: OnFavoriteClicked?
) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(city: String?) {
        if (city != null) {
            binding.imgAddNewFavorite.visibility = View.INVISIBLE
            binding.txtFavoriteCity.visibility = View.VISIBLE
            binding.txtFavoriteCity.text = city

            binding.root.setOnClickListener {
                callback?.onFavoriteSelected(city)
            }
        } else {
            binding.imgAddNewFavorite.visibility = View.VISIBLE
            binding.txtFavoriteCity.visibility = View.INVISIBLE
            binding.root.setOnClickListener {
                callback?.onAddNewFavorite()
            }
        }
    }
}

interface OnFavoriteClicked {

    fun onFavoriteSelected(city: String)

    fun onAddNewFavorite()
}
