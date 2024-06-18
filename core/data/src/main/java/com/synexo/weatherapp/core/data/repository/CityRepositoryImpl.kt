package com.synexo.weatherapp.core.data.repository

import com.synexo.weatherapp.core.data.util.mapToEntity
import com.synexo.weatherapp.core.data.util.toDomainModel
import com.synexo.weatherapp.core.data.util.toWeatherResponse
import com.synexo.weatherapp.core.database.dao.WeatherDao
import com.synexo.weatherapp.core.database.entity.CityEntity
import com.synexo.weatherapp.core.domain.repository.CityRepository
import com.synexo.weatherapp.core.model.CityShortData
import com.synexo.weatherapp.core.model.CityWeather
import com.synexo.weatherapp.core.model.WeatherUnits
import com.synexo.weatherapp.core.model.weather.CityDetails
import com.synexo.weatherapp.core.model.weather.Weather
import com.synexo.weatherapp.core.network.model.weather.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

internal class CityRepositoryImpl @Inject constructor(
    private val weatherDao: WeatherDao,
    private val json: Json
) : CityRepository {

    override suspend fun saveCityData(cityWeather: CityWeather) {
        val weatherResponse = json
            .encodeToString(WeatherResponse.serializer(), cityWeather.weather.toWeatherResponse())
        val order = weatherDao.getMaxOrder()?.plus(1) ?: 0
        weatherDao.saveWeatherEntity(cityWeather.mapToEntity(order, weatherResponse))
    }

    override suspend fun getCityDataOrder(id: String): Int {
        return weatherDao.getOrder(id) ?: -1
    }

    override suspend fun updateCityData(cityWeather: CityWeather) {
        val weatherResponse = json
            .encodeToString(WeatherResponse.serializer(), cityWeather.weather.toWeatherResponse())
        weatherDao.updateWeatherEntity(cityWeather.mapToEntity(null, weatherResponse))
    }

    override suspend fun updateCityData(
        id: String,
        weather: Weather,
        cityDetails: CityDetails?
    ) {
        val weatherResponse =
            json.encodeToString(WeatherResponse.serializer(), weather.toWeatherResponse())
        weatherDao.updateWeatherData(
            id = id,
            weather = weatherResponse,
            countryName = cityDetails?.country,
            adminArea = cityDetails?.administrativeArea,
            cityName = cityDetails?.name,
            language = cityDetails?.language
        )
    }

    override suspend fun getAllCityData(weatherUnits: WeatherUnits): Flow<List<CityWeather>> {
        return weatherDao
            .getSavedCityWeatherEntities()
            .map { entities ->
                entities.map { entity ->
                    val weatherResponse = json
                        .decodeFromString(WeatherResponse.serializer(), entity.weather)
                    val weather = weatherResponse.toDomainModel(weatherUnits)
                    entity.toDomainModel(weather)
                }
            }
    }

    override suspend fun getAllCityShortData(): List<CityShortData> {
        return weatherDao
            .getAllLatLng()
            .map { it.toDomainModel() }
    }

}

private fun CityEntity.toDomainModel(weather: Weather): CityWeather =
    CityWeather(
        weather = weather,
        cityDetails = CityDetails(
            name = cityName,
            country = countryName,
            administrativeArea = adminArea,
            language = language
        ),
        order = order,
        lat = lat,
        lng = lon,
        id = id
    )