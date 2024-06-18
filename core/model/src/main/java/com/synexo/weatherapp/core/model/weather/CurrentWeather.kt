package com.synexo.weatherapp.core.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class CurrentWeather(
    val timezone: String,
    val clouds: Int,
    val dewPoint: Temperature,
    val timestamp: Long,
    val feelsLike: Temperature,
    val humidity: Percent,
    val pressure: Pressure,
    val sunrise: Long,
    val sunset: Long,
    val temp: Temperature,
    val uvi: UVIndex,
    val details: Details,
    val windDeg: Int,
    val windSpeed: WindSpeed,
    val visibility: Visibility? = null,
    val windGust: WindSpeed? = null,
    val rain: Rain? = null,
    val snow: Snow? = null
): Parcelable {

    fun getTimestamp(): Time {
        return Time(timestamp, timezone)
    }

    fun getSunrise(): Time {
        return Time(sunrise, timezone)
    }

    fun getSunset(): Time {
        return Time(sunset, timezone)
    }
}