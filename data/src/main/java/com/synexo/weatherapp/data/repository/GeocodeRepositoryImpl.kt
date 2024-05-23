package com.synexo.weatherapp.data.repository

import com.synexo.weatherapp.data.BuildConfig
import com.synexo.weatherapp.data.api.GeocodeApi
import com.synexo.weatherapp.data.util.safeApiCall
import com.synexo.weatherapp.domain.model.GeocodeModel
import com.synexo.weatherapp.domain.repository.GeocodeRepository
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