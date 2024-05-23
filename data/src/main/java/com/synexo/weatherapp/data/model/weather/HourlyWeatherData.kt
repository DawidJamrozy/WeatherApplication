package com.synexo.weatherapp.data.model.weather


import com.synexo.weatherapp.domain.model.Units
import com.synexo.weatherapp.domain.model.WeatherUnits
import com.synexo.weatherapp.domain.model.weather.HourlyWeather
import com.synexo.weatherapp.domain.model.weather.Pressure
import com.synexo.weatherapp.domain.model.weather.Temperature
import com.synexo.weatherapp.domain.model.weather.UVIndex
import com.synexo.weatherapp.domain.model.weather.Visibility
import com.synexo.weatherapp.domain.model.weather.WindSpeed
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class HourlyWeatherData(
    @SerialName("clouds")
    val clouds: Int,
    @SerialName("dew_point")
    val dewPoint: Double,
    @SerialName("dt")
    val timestamp: Long,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("pop")
    val pop: Double,
    @SerialName("pressure")
    val pressure: Int,
    @SerialName("rain")
    val rain: Rain? = null,
    @SerialName("snow")
    val snow: Snow? = null,
    @SerialName("temp")
    val temp: Double,
    @SerialName("uvi")
    val uvi: Double,
    @SerialName("visibility")
    val visibility: Int? = null,
    @SerialName("weather")
    val details: List<DetailsData>,
    @SerialName("wind_deg")
    val windDeg: Int,
    @SerialName("wind_gust")
    val windGust: Double? = null,
    @SerialName("wind_speed")
    val windSpeed: Double
) {
    fun toDomainModel(units: Units, weatherUnits: WeatherUnits, timezone: String): HourlyWeather =
        HourlyWeather(
            timestamp = timestamp,
            timezone = timezone,
            clouds = clouds,
            dewPoint = Temperature(dewPoint, weatherUnits.degrees, units),
            feelsLike = Temperature(feelsLike, weatherUnits.degrees, units),
            humidity = humidity,
            pop = pop,
            pressure = Pressure(pressure, weatherUnits.pressure, units),
            rain = rain?.toDomainModel(),
            snow = snow?.toDomainModel(),
            temp = Temperature(temp, weatherUnits.degrees, units),
            uvi = UVIndex(uvi),
            visibility = visibility?.let { Visibility(it) },
            details = details.first().toDomainModel(),
            windDeg = windDeg,
            windGust = windGust?.let { WindSpeed(it, weatherUnits.wind, units) },
            windSpeed = WindSpeed(windSpeed, weatherUnits.wind, units)
        )
}