package com.synexo.weatherapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.synexo.weatherapp.core.database.dao.WeatherDao
import com.synexo.weatherapp.core.database.entity.CityEntity

@Database(
    version = 2,
    entities = [
        CityEntity::class,
    ],
    exportSchema = false
)
//@TypeConverters(KotlinXSerializationBasedConverters::class)
internal abstract class WeatherDatabase : RoomDatabase() {

    abstract fun getWeatherDao(): WeatherDao

}

/*
internal class KotlinXSerializationBasedConverters {

    @TypeConverter
    fun stringToWeatherResponse(value: String): WeatherResponse =
        Json.decodeFromString(WeatherResponse.serializer(), value)

    @TypeConverter
    fun weatherResponseToString(value: WeatherResponse): String =
        Json.encodeToString(WeatherResponse.serializer(), value)

}*/
