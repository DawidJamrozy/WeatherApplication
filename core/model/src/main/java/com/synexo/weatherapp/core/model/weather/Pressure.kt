package com.synexo.weatherapp.core.model.weather

import android.os.Parcelable
import com.synexo.weatherapp.core.model.PressureUnit
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
class Pressure(
    val rawValue: Int,
    private val pressureUnit: PressureUnit
) : Parcelable {

    fun getValueWithUnit(): String {
        return when (pressureUnit) {
            PressureUnit.Hectopascals -> "${getValue()} hPa"
            PressureUnit.MillimetersOfMercury -> "${getValue()} mmHg"
        }
    }

    fun getValue(): Int {
        return when (pressureUnit) {
            PressureUnit.Hectopascals -> getAsHectoPascal()
            PressureUnit.MillimetersOfMercury -> getAsMillimeterOfMercury()
        }
    }


    // // 01.04.2024 in both cases pressure is returned in hPa
    private fun getAsHectoPascal(): Int {
        return rawValue
    }


    private fun getAsMillimeterOfMercury(): Int {
        return rawValue.times(0.750062).toInt()
    }
}