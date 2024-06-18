package com.synexo.weatherapp.core.domain.repository

import com.synexo.weatherapp.core.model.CityShortData
import com.synexo.weatherapp.core.model.CityWeather
import com.synexo.weatherapp.core.model.WeatherUnits
import com.synexo.weatherapp.core.model.weather.CityDetails
import com.synexo.weatherapp.core.model.weather.Weather
import kotlinx.coroutines.flow.Flow

interface CityRepository {

    suspend fun saveCityData(cityWeather: CityWeather)

    suspend fun updateCityData(cityWeather: CityWeather)

    suspend fun updateCityData(
        id: String,
        weather: Weather,
        cityDetails: CityDetails?
    )

    suspend fun getAllCityData(weatherUnits: WeatherUnits): Flow<List<CityWeather>>

    suspend fun getAllCityShortData(): List<CityShortData>

    suspend fun getCityDataOrder(id: String): Int

}