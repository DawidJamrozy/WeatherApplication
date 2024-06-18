package com.synexo.weatherapp.myLocations.domain.repository

interface CityDataRepository {

    suspend fun deleteCityData(id: String)

    suspend fun updateCityDataOrder(orderMap: Map<String, Int>)

}