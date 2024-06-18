package com.synexo.weatherapp.core.model

import android.os.Parcelable
import com.synexo.weatherapp.core.model.weather.CityDetails
import com.synexo.weatherapp.core.model.weather.Weather
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityWeather(
    val cityDetails: CityDetails,
    val weather: Weather,
    val lat: Double,
    val lng: Double,
    val order: Int,
    val id: String,
): Parcelable