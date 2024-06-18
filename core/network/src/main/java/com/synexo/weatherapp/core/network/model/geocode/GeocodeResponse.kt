package com.synexo.weatherapp.core.network.model.geocode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeocodeResponse(
    @SerialName("results")
    val results: List<Result>,
    @SerialName("status")
    val status: String
)