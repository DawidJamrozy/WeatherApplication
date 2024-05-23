package com.synexo.weatherapp.domain.model

import android.os.Parcelable
import com.synexo.weatherapp.domain.model.weather.CityDetails
import com.synexo.weatherapp.domain.model.weather.Weather
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