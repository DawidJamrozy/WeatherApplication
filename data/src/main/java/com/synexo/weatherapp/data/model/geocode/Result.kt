package com.synexo.weatherapp.data.model.geocode


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Result(
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