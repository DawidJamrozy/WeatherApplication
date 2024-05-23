package com.synexo.weatherapp.data.model.geocode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AddressComponent(
    @SerialName("long_name")
    val longName: String,
    @SerialName("short_name")
    val shortName: String,
    @SerialName("types")
    val types: List<String>
)