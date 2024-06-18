package com.synexo.weatherapp.core.network.model.weather

import com.synexo.weatherapp.core.model.WeatherUnits
import com.synexo.weatherapp.core.model.weather.Weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("current")
    val currentWeatherData: CurrentWeatherData,
    @SerialName("daily")
    val dailyWeatherData: List<DailyWeatherData>,
    @SerialName("hourly")
    val hourlyWeatherData: List<HourlyWeatherData>,
    @SerialName("timezone")
    val timezone: String,
    @SerialName("timezone_offset")
    val timezoneOffset: Int
) {
    fun toDomainModel(
        weatherUnits: WeatherUnits,
    ): Weather =
        Weather(
            currentWeather = currentWeatherData.toDomainModel(weatherUnits, timezone),
            dailyWeather = dailyWeatherData.map { it.toDomainModel(weatherUnits, timezone) },
            hourlyWeather = hourlyWeatherData.map { it.toDomainModel(weatherUnits, timezone) },
            timezone = timezone,
            timezoneOffset = timezoneOffset,
            weatherUnits = weatherUnits,
        )
}