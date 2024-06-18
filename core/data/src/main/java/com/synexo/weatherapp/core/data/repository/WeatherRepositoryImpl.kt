package com.synexo.weatherapp.core.data.repository

import com.synexo.weatherapp.core.data.BuildConfig
import com.synexo.weatherapp.core.domain.repository.WeatherRepository
import com.synexo.weatherapp.core.model.WeatherUnits
import com.synexo.weatherapp.core.model.weather.Weather
import com.synexo.weatherapp.core.network.api.WeatherApi
import com.synexo.weatherapp.core.network.util.safeApiCall
import javax.inject.Inject

internal class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
) : WeatherRepository {

    override suspend fun getWeatherData(
        lat: Double,
        lng: Double,
        weatherUnits: WeatherUnits,
        lang: String
    ): Weather = safeApiCall {
        weatherApi
            .getWeatherData(
                lat = lat,
                lon = lng,
                units = "metric",
                appId = BuildConfig.OPEN_WEATHER_API_KEY,
                lang = lang
            )
            .toDomainModel(
                weatherUnits = weatherUnits
            )
    }
}