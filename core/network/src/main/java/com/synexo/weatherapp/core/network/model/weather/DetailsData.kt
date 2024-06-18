package com.synexo.weatherapp.core.network.model.weather


import com.synexo.weatherapp.core.model.weather.Details
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailsData(
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