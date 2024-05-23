package com.synexo.weatherapp.domain.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class DailyWeather(
    val timezone: String,
    val clouds: Int,
    val dewPoint: Temperature,
    val timestamp: Long,
    val feelsLike: FeelsLike,
    val humidity: Int,
    val moonPhase: Double,
    val moonrise: Long,
    val moonset: Long,
    val pop: Double,
    val pressure: Pressure,
    val summary: String,
    val sunrise: Long,
    val sunset: Long,
    val temp: Temp,
    val uvi: UVIndex,
    val details: Details,
    val windDeg: Int,
    val windSpeed: WindSpeed,
    val windGust: WindSpeed? = null,
    val rain: Double? = null,
    val snow: Double? = null,
    val visibility: Visibility? = null,
): Parcelable {

    fun getTimestamp(): Time {
        return Time(timestamp, timezone)
    }

    fun getMoonrise(): Time {
        return Time(moonrise, timezone)
    }

    fun getMoonset(): Time {
        return Time(moonset, timezone)
    }

    fun getSunrise(): Time {
        return Time(sunrise, timezone)
    }

    fun getSunset(): Time {
        return Time(sunset, timezone)
    }

}