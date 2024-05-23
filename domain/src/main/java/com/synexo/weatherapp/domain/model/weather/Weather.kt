package com.synexo.weatherapp.domain.model.weather

import android.os.Parcelable
import com.synexo.weatherapp.domain.model.Units
import com.synexo.weatherapp.domain.model.WeatherUnits
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val currentWeather: CurrentWeather,
    val dailyWeather: List<DailyWeather>,
    val hourlyWeather: List<HourlyWeather>,
    val weatherUnits: WeatherUnits,
    val timezone: String,
    val timezoneOffset: Int,
    val units: Units,
) : Parcelable