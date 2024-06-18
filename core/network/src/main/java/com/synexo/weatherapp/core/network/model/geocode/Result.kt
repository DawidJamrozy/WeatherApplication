package com.synexo.weatherapp.core.network.model.geocode


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("address_components")
    val addressComponents: List<AddressComponent>,
    @SerialName("formatted_address")
    val formattedAddress: String,
    @SerialName("geometry")
    val geometry: Geometry,
    @SerialName("place_id")
    val placeId: String,
    @SerialName("types")
    val types: List<String>
)