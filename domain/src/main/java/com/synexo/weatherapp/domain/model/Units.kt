package com.synexo.weatherapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
sealed class Units(val name: String): Parcelable {
    @Parcelize
    @Serializable
    data object Metric : Units("metric")
    @Parcelize
    @Serializable
    data object Imperial : Units("imperial")

}