package com.rob.simpleweather.favorites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rob.simpleweather.model.Lce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class FavoritesViewModel @ViewModelInject constructor(private val manager: FavoritesManager) : ViewModel() {

    val favorites : LiveData<Lce<List<String>>> =
        manager.getFavoriteCities()
            .flowOn(Dispatchers.IO)
            .map { Lce.Success(it) as Lce<List<String>> }
            .onStart { emit(Lce.Loading) }
            .asLiveData()

    fun markCityAsFavorite(city: String, shouldAdd: Boolean) {
        viewModelScope.launch {
            if(shouldAdd) {
                manager.addCityToFavorite(city)
            } else {
                manager.removeCityFromFavorites(city)
            }
        }
    }
}
