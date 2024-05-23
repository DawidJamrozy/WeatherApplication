package com.synexo.weatherapp.data.model.geocode


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Location(
    @SerialName("lat")
    val lat: Double,
    @SerialName("lng")
    val lng: Double
)