package com.synexo.weatherapp.domain.repository

import com.synexo.weatherapp.domain.model.CityNameSearchData

interface SearchRepository {

    suspend fun searchCity(cityName: String): List<CityNameSearchData>

}