package com.rob.simpleweather.repository


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rob.simpleweather.model.Lce
import com.rob.simpleweather.model.SearchEntry
import com.rob.simpleweather.repository.WeatherApi
import kotlinx.coroutines.launch
import timber.log.Timber

class CitySearchViewModel @ViewModelInject constructor(private val weatherApi: WeatherApi) :
    ViewModel() {

    private val _entries = MutableLiveData<Lce<List<SearchEntry>>>()

    val entries: LiveData<Lce<List<SearchEntry>>>
        get() = _entries

    fun searchCities(query: String) {
        _entries.postValue(Lce.Loading)

        viewModelScope.launch {
            try {
                val result = weatherApi.fetchCitiesFor(query)
                Timber.i("Search Success")
                _entries.postValue(Lce.Success(result))
            } catch (e: Exception) {
                Timber.e(e)
                _entries.postValue(Lce.Error(e))
            }
        }
    }
}
