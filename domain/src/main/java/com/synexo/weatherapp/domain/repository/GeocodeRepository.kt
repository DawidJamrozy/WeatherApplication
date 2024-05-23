package com.synexo.weatherapp.domain.repository

import com.synexo.weatherapp.domain.model.GeocodeModel


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