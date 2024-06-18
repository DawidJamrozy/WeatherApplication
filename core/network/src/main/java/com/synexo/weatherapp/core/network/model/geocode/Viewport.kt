package com.synexo.weatherapp.core.network.model.geocode


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Viewport(
    @SerialName("northeast")
    val northeast: Northeast,
    @SerialName("southwest")
    val southwest: Southwest
)