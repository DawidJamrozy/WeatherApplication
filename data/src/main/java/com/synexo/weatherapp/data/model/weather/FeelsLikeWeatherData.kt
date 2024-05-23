package com.synexo.weatherapp.data.model.weather

import com.synexo.weatherapp.domain.model.DegreesUnit
import com.synexo.weatherapp.domain.model.Units
import com.synexo.weatherapp.domain.model.weather.FeelsLike
import com.synexo.weatherapp.domain.model.weather.Temperature
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FeelsLikeWeatherData(
    @SerialName("day")
    val day: Double,
    @SerialName("eve")
    val eve: Double,
    @SerialName("morn")
    val morn: Double,
    @SerialName("night")
    val night: Double
) {
    fun toDomainModel(units: Units, degreesUnit: DegreesUnit) = FeelsLike(
        day = Temperature(
            rawValue = day,
            degreesUnit = degreesUnit,
            units = units
        ),
        eve = Temperature(
            rawValue = eve,
            degreesUnit = degreesUnit,
            units = units
        ),
        morn = Temperature(
            rawValue = morn,
            degreesUnit = degreesUnit,
            units = units
        ),
        night = Temperature(
            rawValue = night,
            degreesUnit = degreesUnit,
            units = units
        )
    )
}