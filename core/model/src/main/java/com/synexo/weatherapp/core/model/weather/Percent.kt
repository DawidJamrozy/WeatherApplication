package com.synexo.weatherapp.core.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
class Percent(
    val value: Int
): Parcelable {

    fun getAsString(): String {
        return "${value}%"
    }
}