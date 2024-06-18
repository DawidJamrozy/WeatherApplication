package com.synexo.weatherapp.core.domain.model

import com.synexo.weatherapp.core.model.weather.CityDetails

data class GeocodeModel(
    val city: String,
    val country: String,
    val administrativeArea: String?,
    val placeId: String,
    val location: Location
) {
    fun mapToCityDetails(language: String): CityDetails = CityDetails(
        name = city,
        country = country,
        administrativeArea = administrativeArea,
        language = language
    )
}