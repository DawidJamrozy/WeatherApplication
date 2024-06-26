package com.synexo.weatherapp.core.network.model.weather


import com.synexo.weatherapp.core.model.WeatherUnits
import com.synexo.weatherapp.core.model.weather.CurrentWeather
import com.synexo.weatherapp.core.model.weather.Percent
import com.synexo.weatherapp.core.model.weather.Pressure
import com.synexo.weatherapp.core.model.weather.Temperature
import com.synexo.weatherapp.core.model.weather.UVIndex
import com.synexo.weatherapp.core.model.weather.Visibility
import com.synexo.weatherapp.core.model.weather.WindSpeed
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherData(
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
    @SerialName("pressure")
    val pressure: Int,
    @SerialName("sunrise")
    val sunrise: Long,
    @SerialName("sunset")
    val sunset: Long,
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
    @SerialName("wind_speed")
    val windSpeed: Double,
    @SerialName("wind_gust")
    val windGust: Double? = null,
    @SerialName("rain")
    val rain: Rain? = null,
    @SerialName("snow")
    val snow: Snow? = null
) {
    fun toDomainModel(weatherUnits: WeatherUnits, timezone: String): CurrentWeather {
        return CurrentWeather(
            timestamp = timestamp,
            timezone = timezone,
            clouds = clouds,
            dewPoint = Temperature(
                dewPoint,
                weatherUnits.degrees
            ),
            feelsLike = Temperature(
                feelsLike,
                weatherUnits.degrees
            ),
            humidity = Percent(humidity),
            pressure = Pressure(
                pressure,
                weatherUnits.pressure
            ),
            sunrise = sunrise,
            sunset = sunset,
            temp = Temperature(temp, weatherUnits.degrees),
            uvi = UVIndex(uvi),
            visibility = visibility?.let { Visibility(it) },
            details = details.first().toDomainModel(),
            windDeg = windDeg,
            windSpeed = WindSpeed(windSpeed,weatherUnits.wind),
            windGust = windGust?.let { WindSpeed(it, weatherUnits.wind) },
            rain = rain?.toDomainModel(),
            snow = snow?.toDomainModel()
        )
    }
}