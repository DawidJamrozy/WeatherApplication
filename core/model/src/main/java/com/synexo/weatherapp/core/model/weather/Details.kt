package com.synexo.weatherapp.core.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class Details(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
): Parcelable