package com.synexo.weatherapp.core.data.repository

import com.synexo.weatherapp.core.data.BuildConfig
import com.synexo.weatherapp.core.data.util.toGeocodeModel
import com.synexo.weatherapp.core.domain.model.GeocodeModel
import com.synexo.weatherapp.core.domain.repository.GeocodeRepository
import com.synexo.weatherapp.core.network.api.GeocodeApi
import com.synexo.weatherapp.core.network.util.safeApiCall
import javax.inject.Inject

internal class GeocodeRepositoryImpl @Inject constructor(
    private val geocodeApi: GeocodeApi
) : GeocodeRepository {

    private companion object {
        const val LOCATION_TYPE = "APPROXIMATE"
        const val RESULT_TYPE = "locality"
    }

    override suspend fun getGeocodeDataFromLatLng(
        lat: Double,
        lng: Double,
        lang: String,
    ): GeocodeModel = safeApiCall {
        geocodeApi
            .getGeocodeData(
                latlng = "${lat},${lng}",
                locationType = LOCATION_TYPE,
                resultType = RESULT_TYPE,
                lang = lang,
                key = BuildConfig.GOOGLE_API_KEY,
            )
            .toGeocodeModel()
    }

    override suspend fun getGeocodeData(
        name: String,
        lang: String
    ): GeocodeModel = safeApiCall {
        geocodeApi
            .getGeocodeData(
                address = name,
                key = BuildConfig.GOOGLE_API_KEY,
                lang = lang
            )
            .toGeocodeModel()
    }

}