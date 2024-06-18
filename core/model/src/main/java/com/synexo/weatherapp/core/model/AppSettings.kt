package com.synexo.weatherapp.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Serializable
@Parcelize
data class AppSettings(
    val degrees: DegreesUnit,
    val wind: WindUnit,
    val pressure: PressureUnit,
    val theme: Theme
):Parcelable {
    fun mapToWeatherUnits(): WeatherUnits {
        return WeatherUnits(degrees, wind, pressure)
    }
}

@Serializable
@Parcelize
sealed class Theme(val type: String): Parcelable {
    @Serializable
    @Parcelize
    data object Light : Theme("Light")
    @Serializable
    @Parcelize
    data object Dark : Theme("Dark")
    @Serializable
    @Parcelize
    data object System : Theme("System")
}

@Serializable
@Parcelize
sealed class WindUnit(val name: String): Parcelable {
    @Serializable
    @Parcelize
    data object MetersPerSecond : WindUnit("m/s")
    @Serializable
    @Parcelize
    data object KilometersPerHour : WindUnit("km/h")
}

@Serializable
@Parcelize
sealed class PressureUnit(val name: String): Parcelable {
    @Serializable
    @Parcelize
    data object Hectopascals : PressureUnit("hPa")
    @Serializable
    @Parcelize
    data object MillimetersOfMercury : PressureUnit("mmHg")
}

@Serializable
@Parcelize
sealed class DegreesUnit(val name: String): Parcelable {
    @Serializable
    @Parcelize
    data object Celsius : DegreesUnit("C")
    @Serializable
    @Parcelize
    data object Fahrenheit : DegreesUnit("F")
}

