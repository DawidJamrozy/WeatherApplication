package com.synexo.weatherapp.data.model.weather


import com.synexo.weatherapp.domain.model.weather.Snow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Snow(
    @SerialName("1h")
    val h: Double
) {
    fun toDomainModel(): Snow =
        Snow(
            h = h
        )
}