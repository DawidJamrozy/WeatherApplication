package com.synexo.weatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.synexo.weatherapp.data.database.dao.WeatherDao
import com.synexo.weatherapp.data.database.entity.CityEntity
import com.synexo.weatherapp.data.model.weather.WeatherResponse
import com.synexo.weatherapp.domain.model.Units
import kotlinx.serialization.json.Json

@Database(
    version = 2,
    entities = [
        CityEntity::class,
    ],
    exportSchema = false
)
@TypeConverters(KotlinXSerializationBasedConverters::class)
internal abstract class WeatherDatabase : RoomDatabase() {

    abstract fun getWeatherDao(): WeatherDao

}

internal class KotlinXSerializationBasedConverters {

    @TypeConverter
    fun stringToUnits(value: String): Units =
        Json.decodeFromString(Units.serializer(), value)

    @TypeConverter
    fun unitsToString(value: Units): String =
        Json.encodeToString(Units.serializer(), value)

    @TypeConverter
    fun stringToWeatherResponse(value: String): WeatherResponse =
        Json.decodeFromString(WeatherResponse.serializer(), value)

    @TypeConverter
    fun weatherResponseToString(value: WeatherResponse): String =
        Json.encodeToString(WeatherResponse.serializer(), value)

}