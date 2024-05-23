package com.synexo.weatherapp.data.repository

import com.synexo.weatherapp.data.BuildConfig
import com.synexo.weatherapp.data.api.WeatherApi
import com.synexo.weatherapp.data.util.safeApiCall
import com.synexo.weatherapp.domain.model.Units
import com.synexo.weatherapp.domain.model.WeatherUnits
import com.synexo.weatherapp.domain.model.weather.Weather
import com.synexo.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

internal class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
) : WeatherRepository {

    override suspend fun getWeatherData(
        lat: Double,
        lng: Double,
        units: Units,
        weatherUnits: WeatherUnits,
        lang: String
    ): Weather = safeApiCall {
        weatherApi
            .getWeatherData(
                lat = lat,
                lon = lng,
                units = units.name,
                appId = BuildConfig.OPEN_WEATHER_API_KEY,
                lang = lang
            )
            .toDomainModel(
                units = units,
                weatherUnits = weatherUnits
            )
    }
}