package com.synexo.weatherapp.core.network.model.weather


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Snow(
    @SerialName("1h")
    val h: Double
) {
    fun toDomainModel(): com.synexo.weatherapp.core.model.weather.Snow =
        com.synexo.weatherapp.core.model.weather.Snow(
            h = h
        )
}