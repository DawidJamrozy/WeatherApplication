package com.synexo.weatherapp.data.model.weather


import com.synexo.weatherapp.domain.model.weather.Rain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Rain(
    @SerialName("1h")
    val h: Double
) {
    fun toDomainModel(): Rain =
        Rain(
            h = h
        )
}