package com.synexo.weatherapp.domain.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class HourlyWeather(
    val timezone: String,
    val clouds: Int,
    val dewPoint: Temperature,
    val timestamp: Long,
    val feelsLike: Temperature,
    val humidity: Int,
    val pop: Double,
    val pressure: Pressure,
    val rain: Rain? = null,
    val snow: Snow? = null,
    val temp: Temperature,
    val uvi: UVIndex,
    val visibility: Visibility? = null,
    val details: Details,
    val windDeg: Int,
    val windGust: WindSpeed? = null,
    val windSpeed: WindSpeed
): Parcelable {

    fun getTimestamp(): Time {
        return Time(timestamp, timezone)
    }
}