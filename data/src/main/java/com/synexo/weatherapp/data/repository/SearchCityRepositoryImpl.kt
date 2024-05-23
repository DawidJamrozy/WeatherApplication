package com.synexo.weatherapp.data.repository

import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.ktx.api.net.awaitFindAutocompletePredictions
import com.synexo.weatherapp.core.AppError
import com.synexo.weatherapp.domain.model.CityNameSearchData
import com.synexo.weatherapp.domain.repository.SearchRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

internal class SearchCityRepositoryImpl @Inject constructor(
    private val placesClient: PlacesClient
) : SearchRepository {

    override suspend fun searchCity(cityName: String): List<CityNameSearchData> {
        return try {
            createAutocompletePrediction(cityName)
                .autocompletePredictions
                .map {
                    CityNameSearchData(
                        placeId = it.placeId,
                        primaryText = it.getPrimaryText(null).toString(),
                        secondaryText = it.getSecondaryText(null).toString()
                    )
                }
        } catch (e: Exception) {
            if (e is ApiException) {
                throw AppError.GoogleApiException(e.statusCode)
            } else {
                throw AppError.UnknownError(e.message)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun createAutocompletePrediction(
        inputQuery: String
    ): FindAutocompletePredictionsResponse {
        return placesClient
            .awaitFindAutocompletePredictions {
                typesFilter = listOf(PlaceTypes.CITIES)
                sessionToken = AutocompleteSessionToken.newInstance()
                query = inputQuery
            }
    }

}