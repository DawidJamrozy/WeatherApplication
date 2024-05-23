package com.synexo.weatherapp.data.model.weather


import com.synexo.weatherapp.domain.model.Units
import com.synexo.weatherapp.domain.model.WeatherUnits
import com.synexo.weatherapp.domain.model.weather.DailyWeather
import com.synexo.weatherapp.domain.model.weather.Pressure
import com.synexo.weatherapp.domain.model.weather.Temperature
import com.synexo.weatherapp.domain.model.weather.UVIndex
import com.synexo.weatherapp.domain.model.weather.Visibility
import com.synexo.weatherapp.domain.model.weather.WindSpeed
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class DailyWeatherData(
    @SerialName("clouds")
    val clouds: Int,
    @SerialName("dew_point")
    val dewPoint: Double,
    @SerialName("dt")
    val timestamp: Long,
    @SerialName("feels_like")
    val feelsLikeWeatherData: FeelsLikeWeatherData,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("moon_phase")
    val moonPhase: Double,
    @SerialName("moonrise")
    val moonrise: Long,
    @SerialName("moonset")
    val moonset: Long,
    @SerialName("pop")
    val pop: Double,
    @SerialName("pressure")
    val pressure: Int,
    @SerialName("summary")
    val summary: String,
    @SerialName("sunrise")
    val sunrise: Long,
    @SerialName("sunset")
    val sunset: Long,
    @SerialName("temp")
    val tempWeatherData: TempWeatherData,
    @SerialName("uvi")
    val uvi: Double,
    @SerialName("weather")
    val details: List<DetailsData>,
    @SerialName("wind_deg")
    val windDeg: Int,
    @SerialName("wind_speed")
    val windSpeed: Double,
    @SerialName("wind_gust")
    val windGust: Double? = null,
    @SerialName("rain")
    val rain: Double? = null,
    @SerialName("snow")
    val snow: Double? = null,
    @SerialName("visibility")
    val visibility: Int? = null,
) {
    fun toDomainModel(units: Units, weatherUnits: WeatherUnits, timezone: String): DailyWeather =
        DailyWeather(
            timestamp = timestamp,
            timezone = timezone,
            clouds = clouds,
            dewPoint = Temperature(dewPoint, weatherUnits.degrees, units),
            feelsLike = feelsLikeWeatherData.toDomainModel(units, weatherUnits.degrees),
            humidity = humidity,
            moonPhase = moonPhase,
            moonrise = moonrise,
            moonset = moonset,
            pop = pop,
            pressure = Pressure(pressure,  weatherUnits.pressure,units),
            summary = summary,
            sunrise = sunrise,
            sunset = sunset,
            temp = tempWeatherData.toDomainModel(units, weatherUnits.degrees),
            uvi = UVIndex(uvi),
            details = details.first().toDomainModel(),
            windDeg = windDeg,
            windSpeed = WindSpeed(windSpeed,  weatherUnits.wind, units),
            windGust = windGust?.let { WindSpeed(it,  weatherUnits.wind, units) },
            rain = rain,
            snow = snow,
            visibility = visibility?.let { Visibility(it) }
        )
}