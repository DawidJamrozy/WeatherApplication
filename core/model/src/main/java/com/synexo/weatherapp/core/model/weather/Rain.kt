package com.synexo.weatherapp.core.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class Rain(
    val h: Double
): Parcelable