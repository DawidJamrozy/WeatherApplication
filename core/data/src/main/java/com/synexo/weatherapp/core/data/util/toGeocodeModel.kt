package com.synexo.weatherapp.core.data.util

import com.synexo.weatherapp.core.domain.model.GeocodeModel
import com.synexo.weatherapp.core.domain.model.Location
import com.synexo.weatherapp.core.network.model.geocode.GeocodeResponse

internal fun GeocodeResponse.toGeocodeModel(): GeocodeModel {
    val result = results.first()
    return GeocodeModel(
        city = result.addressComponents.first { it.types.contains("locality") }.shortName,
        country = result.addressComponents.first { it.types.contains("country") }.longName,
        administrativeArea = result.addressComponents.firstOrNull { it.types.contains("administrative_area_level_1") }?.shortName,
        placeId = result.placeId,
        location = result
            .geometry
            .location
            .let {
                Location(
                    lat = it.lat,
                    lng = it.lng
                )
            }
    )
}