package com.synexo.weatherapp.data.model.geocode


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Geometry(
    @SerialName("location")
    val location: Location,
    @SerialName("location_type")
    val locationType: String,
    @SerialName("viewport")
    val viewport: Viewport
)