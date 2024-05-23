package com.synexo.weatherapp.domain.repository

import com.synexo.weatherapp.domain.model.CityShortData
import com.synexo.weatherapp.domain.model.CityWeather
import com.synexo.weatherapp.domain.model.WeatherUnits
import com.synexo.weatherapp.domain.model.weather.CityDetails
import com.synexo.weatherapp.domain.model.weather.Weather
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

    suspend fun deleteAllCityData()

    suspend fun deleteCityData(id: String)

    suspend fun updateCityDataOrder(orderMap: Map<String, Int>)

}