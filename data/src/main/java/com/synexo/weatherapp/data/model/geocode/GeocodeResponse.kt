package com.synexo.weatherapp.data.model.geocode


import com.synexo.weatherapp.domain.model.GeocodeModel
import com.synexo.weatherapp.domain.model.Location
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GeocodeResponse(
    @SerialName("results")
    val results: List<Result>,
    @SerialName("status")
    val status: String
) {
    fun toGeocodeModel(): GeocodeModel {
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
}