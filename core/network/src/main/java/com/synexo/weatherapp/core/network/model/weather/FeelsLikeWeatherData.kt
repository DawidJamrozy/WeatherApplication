package com.synexo.weatherapp.core.network.model.weather

import com.synexo.weatherapp.core.model.DegreesUnit
import com.synexo.weatherapp.core.model.weather.FeelsLike
import com.synexo.weatherapp.core.model.weather.Temperature
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeelsLikeWeatherData(
    @SerialName("day")
    val day: Double,
    @SerialName("eve")
    val eve: Double,
    @SerialName("morn")
    val morn: Double,
    @SerialName("night")
    val night: Double
) {
    fun toDomainModel(degreesUnit: DegreesUnit) = FeelsLike(
        day = Temperature(
            rawValue = day,
            degreesUnit = degreesUnit
        ),
        eve = Temperature(
            rawValue = eve,
            degreesUnit = degreesUnit
        ),
        morn = Temperature(
            rawValue = morn,
            degreesUnit = degreesUnit
        ),
        night = Temperature(
            rawValue = night,
            degreesUnit = degreesUnit
        )
    )
}