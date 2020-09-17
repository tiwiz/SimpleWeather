package com.rob.simpleweather.model

import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse (
    val location : Location,
    val current : Current,
    val forecast : Forecast
)

@Serializable
data class Location (
    val name : String,
    val region : String,
    val country : String,
    val lat : Double,
    val lon : Double,
    val tz_id : String,
    val localtime_epoch : Int,
    val localtime : String
)

@Serializable
data class Current (
    val last_updated_epoch : Int,
    val last_updated : String,
    val temp_c : Double,
    val temp_f : Double,
    val is_day : Int,
    val condition : Condition,
    val wind_mph : Double,
    val wind_kph : Double,
    val wind_degree : Double,
    val wind_dir : String,
    val pressure_mb : Double,
    val pressure_in : Double,
    val precip_mm : Double,
    val precip_in : Double,
    val humidity : Int,
    val cloud : Int,
    val feelslike_c : Double,
    val feelslike_f : Double,
    val vis_km : Double,
    val vis_miles : Double,
    val uv : Double,
    val gust_mph : Double,
    val gust_kph : Double
)

@Serializable
data class Condition (
    val text : String,
    val icon : String,
    val code : Int
)

@Serializable
data class Forecast (
    val forecastday : List<ForecastDay>
)

@Serializable
data class ForecastDay (
    val date : String,
    val date_epoch : Int,
    val day : Day,
    val astro : Astro
)

@Serializable
data class Day (
    val maxtemp_c : Double,
    val maxtemp_f : Double,
    val mintemp_c : Double,
    val mintemp_f : Double,
    val avgtemp_c : Double,
    val avgtemp_f : Double,
    val maxwind_mph : Double,
    val maxwind_kph : Double,
    val totalprecip_mm : Double,
    val totalprecip_in : Double,
    val avgvis_km : Double,
    val avgvis_miles : Double,
    val avghumidity : Double,
    val daily_will_it_rain : Int,
    val daily_chance_of_rain : Int,
    val daily_will_it_snow : Int,
    val daily_chance_of_snow : Int,
    val condition : Condition,
    val uv : Double
)

@Serializable
data class Astro (
    val sunrise : String,
    val sunset : String,
    val moonrise : String,
    val moonset : String
)
