package com.rob.simpleweather.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rob.simpleweather.databinding.ActivityFavoriteBinding
import com.rob.simpleweather.favorites.FavoritesManager
import com.rob.simpleweather.favorites.FavoritesViewModel
import com.rob.simpleweather.main.search.SearchAdapter
import com.rob.simpleweather.main.search.SearchCallbacks
import com.rob.simpleweather.repository.CitySearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity(), SearchCallbacks {

    @Inject
    lateinit var searchAdapter: SearchAdapter

    @Inject
    lateinit var favoritesManager: FavoritesManager

    private val searchViewModel by viewModels<CitySearchViewModel>()
    private val favoritesViewModel by viewModels<FavoritesViewModel>()

    private val binding by lazy {
        ActivityFavoriteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding.listCities) {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = searchAdapter
        }

        binding.btnSearch.setOnClickListener {
            searchViewModel.searchCities(binding.queryText.text.toString())
        }

        searchViewModel.entries.observe(this) {
            it.doOnData { entries ->
                searchAdapter.updateItems(entries)
            }
        }
    }

    override fun onCitySaved(city: String) {
        lifecycleScope.launch {
            favoritesManager.addCityToFavorite(city)
            finish()
        }
    }
}
