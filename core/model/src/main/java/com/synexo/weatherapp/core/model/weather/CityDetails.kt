package com.synexo.weatherapp.core.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityDetails(
    val name: String,
    val country: String,
    val administrativeArea: String?,
    val language: String,
): Parcelable