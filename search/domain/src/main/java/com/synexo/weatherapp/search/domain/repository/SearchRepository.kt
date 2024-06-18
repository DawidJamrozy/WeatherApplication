package com.synexo.weatherapp.search.domain.repository

import com.synexo.weatherapp.search.domain.model.CityNameSearchData

interface SearchRepository {

    suspend fun searchCity(cityName: String): List<CityNameSearchData>

}