package com.synexo.weatherapp.myLocations.data.repository

import com.synexo.weatherapp.core.database.dao.WeatherDao
import com.synexo.weatherapp.myLocations.domain.repository.CityDataRepository
import javax.inject.Inject

class CityDataRepositoryImpl @Inject constructor(
    private val weatherDao: WeatherDao
): CityDataRepository {

    override suspend fun deleteCityData(id: String) {
        weatherDao.deleteLocation(id)
    }

    override suspend fun updateCityDataOrder(orderMap: Map<String, Int>) {
        weatherDao.updateOrders(orderMap)
    }

}