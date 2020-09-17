package com.rob.simpleweather.repository

import android.content.Context
import androidx.room.*
import com.rob.simpleweather.model.ForecastResponse
import com.rob.simpleweather.model.ForecastTable
import kotlinx.serialization.json.Json
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

class Converters {

    @TypeConverter
    fun fromForecast(value: ForecastResponse?): String? =
        value?.let {
            Json.encodeToString(ForecastResponse.serializer(), it)
        }

    @TypeConverter
    fun jsonToForecast(value: String?): ForecastResponse? =
        value?.let {
            Json.decodeFromString(ForecastResponse.serializer(), it)
        }

    @TypeConverter
    fun fromLocalDate(value: LocalDateTime?): Long? =
        value?.toEpochSecond(ZoneOffset.UTC)

    @TypeConverter
    fun toLocalDate(value: Long?): LocalDateTime? =
        value?.let {
            LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC)
        }
}

@Dao
interface ForecastDao {

    @Query("SELECT * FROM ForecastTable WHERE city = :city LIMIT 1")
    suspend fun findByCity(city: String): ForecastTable?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateCityWeather(forecastTable: ForecastTable)
}

@Database(entities = [ForecastTable::class], version = 1)
@TypeConverters(Converters::class)
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun dao(): ForecastDao

    companion object {
        fun buildDb(context: Context) =
            Room.databaseBuilder(
                context,
                ForecastDatabase::class.java, "forecast-db"
            ).build()

        fun buildInMemoryDb(context: Context) =
            Room.inMemoryDatabaseBuilder(context, ForecastDatabase::class.java).build()
    }
}


