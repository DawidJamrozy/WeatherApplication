package com.synexo.weatherapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchCityDetails(
    val primaryName: String,
    val secondaryName: String,
    val placeId: String,
    val isLoading: Boolean
): Parcelable {
    fun getFullName(): String {
        return "$primaryName, $secondaryName"
    }
}