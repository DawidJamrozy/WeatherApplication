package com.synexo.weatherapp.data.repository

import com.synexo.weatherapp.data.database.dao.WeatherDao
import com.synexo.weatherapp.data.util.mapToEntity
import com.synexo.weatherapp.data.util.toWeatherResponse
import com.synexo.weatherapp.domain.model.CityShortData
import com.synexo.weatherapp.domain.model.CityWeather
import com.synexo.weatherapp.domain.model.WeatherUnits
import com.synexo.weatherapp.domain.model.weather.CityDetails
import com.synexo.weatherapp.domain.model.weather.Weather
import com.synexo.weatherapp.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class CityRepositoryImpl @Inject constructor(
    private val weatherDao: WeatherDao
) : CityRepository {

    override suspend fun saveCityData(cityWeather: CityWeather) {
        val order = weatherDao.getMaxOrder()?.plus(1) ?: 0
        weatherDao.saveWeatherEntity(cityWeather.mapToEntity(order))
    }

    override suspend fun getCityDataOrder(id: String): Int {
        return weatherDao.getOrder(id) ?: -1
    }

    override suspend fun updateCityData(cityWeather: CityWeather) {
        weatherDao.updateWeatherEntity(cityWeather.mapToEntity(null))
    }

    override suspend fun updateCityData(
        id: String,
        weather: Weather,
        cityDetails: CityDetails?
    ) {
        weatherDao.updateWeatherData(
            id = id,
            weather = weather.toWeatherResponse(),
            countryName = cityDetails?.country,
            adminArea = cityDetails?.administrativeArea,
            cityName = cityDetails?.name,
            language = cityDetails?.language
        )
    }

    override suspend fun getAllCityData(weatherUnits: WeatherUnits, ): Flow<List<CityWeather>> =
        weatherDao
            .getSavedCityWeatherEntities()
            .map { entities -> entities.map { entity -> entity.toDomainModel(weatherUnits) }}

    override suspend fun getAllCityShortData(): List<CityShortData> {
        return weatherDao
            .getAllLatLng()
            .map { it.toDomainModel() }
    }

    override suspend fun deleteAllCityData() {
        weatherDao.clearAllLocations()
    }

    override suspend fun deleteCityData(id: String) {
        weatherDao.deleteLocation(id)
    }

    override suspend fun updateCityDataOrder(orderMap: Map<String, Int>) {
        weatherDao.updateOrders(orderMap)
    }

}