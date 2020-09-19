package com.rob.simpleweather.favorites

import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesManager @Inject constructor(private val store: DataStore<Preferences>) {

    private val key = preferencesKey<String>(FAVORITES_KEY)

    fun getFavoriteCities(): Flow<List<String>> =
        store.data.map { preferences ->
            currentValue(preferences)
        }

    private fun currentValue(preferences: Preferences): List<String> {
        return preferences[key]?.let { cities ->
            Json.decodeFromString(ListSerializer(String.serializer()), cities)
        } ?: emptyList()
    }

    suspend fun addCityToFavorite(city: String) {
        updateFavorites { cities -> cities + city }
    }

    private suspend fun updateFavorites(op: (List<String>) -> List<String>) {
        store.edit { preferences ->
            val citiesJson = Json.encodeToString(
                ListSerializer(String.serializer()),
                op(currentValue(preferences))
            )
            preferences[key] = citiesJson
        }
    }

    suspend fun removeCityFromFavorites(city: String) {
        updateFavorites { cities -> cities - city }
    }

    companion object {
        private const val FAVORITES_KEY = "DataStore.Favorites"
    }
}
