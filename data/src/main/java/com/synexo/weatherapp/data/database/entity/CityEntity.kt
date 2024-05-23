package com.synexo.weatherapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.synexo.weatherapp.data.model.weather.WeatherResponse
import com.synexo.weatherapp.domain.model.CityWeather
import com.synexo.weatherapp.domain.model.Units
import com.synexo.weatherapp.domain.model.WeatherUnits
import com.synexo.weatherapp.domain.model.weather.CityDetails

@Entity("city_table")
internal data class CityEntity(

    @ColumnInfo(name = "units")
    val units: Units,

    @ColumnInfo(name = "weather")
    val weather: WeatherResponse,

    @ColumnInfo(name = "city_name")
    val cityName: String,

    @ColumnInfo(name = "lat")
    val lat: Double,

    @ColumnInfo(name = "lon")
    val lon: Double,

    @ColumnInfo(name = "country_name")
    val countryName: String,

    @ColumnInfo(name= "language")
    val language: String,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,

    @ColumnInfo(name = "admin_area")
    val adminArea: String?,

    @ColumnInfo(name = "order")
    val order: Int,

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String
) {

    fun toDomainModel(weatherUnits: WeatherUnits): CityWeather =
        CityWeather(
            weather = weather.toDomainModel(units, weatherUnits),
            cityDetails = CityDetails(
                name = cityName,
                country = countryName,
                administrativeArea = adminArea,
                language = language
            ),
            order = order,
            lat = lat,
            lng = lon,
            id = id
        )
}