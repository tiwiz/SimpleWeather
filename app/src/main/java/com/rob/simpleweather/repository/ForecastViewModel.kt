package com.rob.simpleweather.repository

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rob.simpleweather.model.ForecastResponse
import com.rob.simpleweather.model.Lce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class ForecastViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    private val _forecast = MutableLiveData<Lce<ForecastResponse>>()

    val forecast: LiveData<Lce<ForecastResponse>>
        get() = _forecast

    fun requestForecastFor(city: String, forceFetch: Boolean = false) {
        _forecast.postValue(Lce.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getOrFetch(city, forceFetch)
                _forecast.postValue(Lce.Success(result))
            } catch(e: Exception) {
                Timber.e(e)
                _forecast.postValue(Lce.Error(e))
            }
        }
    }
}
