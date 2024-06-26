package com.synexo.weatherapp.core.network.model.geocode


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Southwest(
    @SerialName("lat")
    val lat: Double,
    @SerialName("lng")
    val lng: Double
)