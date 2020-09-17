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
    val temp_c : Int,
    val temp_f : Double,
    val is_day : Int,
    val condition : Condition,
    val wind_mph : Int,
    val wind_kph : Int,
    val wind_degree : Int,
    val wind_dir : String,
    val pressure_mb : Int,
    val pressure_in : Double,
    val precip_mm : Int,
    val precip_in : Int,
    val humidity : Int,
    val cloud : Int,
    val feelslike_c : Double,
    val feelslike_f : Double,
    val vis_km : Int,
    val vis_miles : Int,
    val uv : Int,
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
    val forecastDay : List<ForecastDay>
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
    val maxwind_mph : Int,
    val maxwind_kph : Double,
    val totalprecip_mm : Int,
    val totalprecip_in : Int,
    val avgvis_km : Int,
    val avgvis_miles : Int,
    val avghumidity : Int,
    val daily_will_it_rain : Int,
    val daily_chance_of_rain : Int,
    val daily_will_it_snow : Int,
    val daily_chance_of_snow : Int,
    val condition : Condition,
    val uv : Int
)

@Serializable
data class Astro (
    val sunrise : String,
    val sunset : String,
    val moonrise : String,
    val moonset : String
)
