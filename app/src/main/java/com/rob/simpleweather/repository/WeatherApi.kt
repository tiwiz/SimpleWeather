package com.rob.simpleweather.repository


import com.rob.simpleweather.model.SearchResponse
import com.rob.simpleweather.model.ForecastResponse
import com.rob.simpleweather.model.SearchEntry
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/v1/forecast.json")
    suspend fun fetchForecastFor(
        @Query("q") city: String,
        @Query("days") days: Int = 3
    ) : ForecastResponse

    @GET("/v1/search.json")
    suspend fun fetchCitiesFor(@Query("q") query: String) : List<SearchEntry>
}
