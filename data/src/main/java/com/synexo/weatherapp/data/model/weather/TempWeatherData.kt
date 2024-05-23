package com.synexo.weatherapp.data.model.weather


import com.synexo.weatherapp.domain.model.DegreesUnit
import com.synexo.weatherapp.domain.model.Units
import com.synexo.weatherapp.domain.model.weather.Temp
import com.synexo.weatherapp.domain.model.weather.Temperature
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TempWeatherData(
    @SerialName("day")
    val day: Double,
    @SerialName("eve")
    val eve: Double,
    @SerialName("max")
    val max: Double,
    @SerialName("min")
    val min: Double,
    @SerialName("morn")
    val morn: Double,
    @SerialName("night")
    val night: Double
) {

    fun toDomainModel(
        units: Units,
        degreesUnit: DegreesUnit
    ): Temp =
        Temp(
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
            ),
            max = Temperature(
                rawValue = max,
                degreesUnit = degreesUnit,
                units = units
            ),
            min = Temperature(
                rawValue = min,
                degreesUnit = degreesUnit,
                units = units
            ),
        )
}