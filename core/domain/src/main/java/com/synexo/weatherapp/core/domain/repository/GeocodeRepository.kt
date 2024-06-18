package com.synexo.weatherapp.core.domain.repository

import com.synexo.weatherapp.core.domain.model.GeocodeModel

interface GeocodeRepository {

    suspend fun getGeocodeDataFromLatLng(
        lat: Double,
        lng: Double,
        lang: String
    ): GeocodeModel

    suspend fun getGeocodeData(
        name: String,
        lang: String
    ): GeocodeModel

}