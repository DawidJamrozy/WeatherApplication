package com.synexo.weatherapp.domain.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Visibility(
    val value: Int
): Parcelable {
    fun getAsString(): String {
        return "${value}m"
    }

}