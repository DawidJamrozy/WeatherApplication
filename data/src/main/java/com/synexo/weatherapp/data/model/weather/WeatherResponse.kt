package com.synexo.weatherapp.data.model.weather


import com.synexo.weatherapp.domain.model.Units
import com.synexo.weatherapp.domain.model.WeatherUnits
import com.synexo.weatherapp.domain.model.weather.Weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class WeatherResponse(
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
        units: Units,
        weatherUnits: WeatherUnits,
    ): Weather =
        Weather(
            currentWeather = currentWeatherData.toDomainModel(units, weatherUnits, timezone),
            dailyWeather = dailyWeatherData.map { it.toDomainModel(units,weatherUnits, timezone) },
            hourlyWeather = hourlyWeatherData.map { it.toDomainModel(units, weatherUnits, timezone) },
            timezone = timezone,
            timezoneOffset = timezoneOffset,
            weatherUnits = weatherUnits,
            units = units,
        )
}