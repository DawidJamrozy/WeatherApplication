package com.synexo.weatherapp.domain.repository

import com.synexo.weatherapp.domain.model.Units
import com.synexo.weatherapp.domain.model.WeatherUnits
import com.synexo.weatherapp.domain.model.weather.Weather

interface WeatherRepository {

    suspend fun getWeatherData(
        lat: Double,
        lng: Double,
        units: Units,
        weatherUnits: WeatherUnits,
        lang: String
    ): Weather

}