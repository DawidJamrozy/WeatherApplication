package com.synexo.weatherapp.data.model.weather


import com.synexo.weatherapp.domain.model.weather.Details
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class DetailsData(
    @SerialName("description")
    val description: String,
    @SerialName("icon")
    val icon: String,
    @SerialName("id")
    val id: Int,
    @SerialName("main")
    val main: String
) {

    fun toDomainModel(): Details =
        Details(
            description = description,
            icon = icon,
            id = id,
            main = main
        )

}