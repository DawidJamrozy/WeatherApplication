package com.synexo.weatherapp.core.model.weather

import android.os.Parcelable
import com.synexo.weatherapp.core.model.WeatherUnits
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Weather(
    val currentWeather: CurrentWeather,
    val dailyWeather: List<DailyWeather>,
    val hourlyWeather: List<HourlyWeather>,
    val weatherUnits: WeatherUnits,
    val timezone: String,
    val timezoneOffset: Int,
) : Parcelable