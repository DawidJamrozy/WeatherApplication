package com.synexo.weatherapp.core.domain.repository

import com.synexo.weatherapp.core.model.WeatherUnits
import com.synexo.weatherapp.core.model.weather.Weather

interface WeatherRepository {

    suspend fun getWeatherData(
        lat: Double,
        lng: Double,
        weatherUnits: WeatherUnits,
        lang: String
    ): Weather

}