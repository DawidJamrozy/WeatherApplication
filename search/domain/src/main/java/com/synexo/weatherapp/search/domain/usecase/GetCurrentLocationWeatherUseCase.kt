package com.synexo.weatherapp.search.domain.usecase

import com.synexo.weatherapp.core.domain.repository.CityRepository
import com.synexo.weatherapp.core.domain.repository.GeocodeRepository
import com.synexo.weatherapp.core.domain.repository.SettingsRepository
import com.synexo.weatherapp.core.domain.repository.WeatherRepository
import com.synexo.weatherapp.core.domain.usecase.base.BaseSuspendUseCase
import com.synexo.weatherapp.core.domain.util.safeTryCatchFlow
import com.synexo.weatherapp.core.model.CityWeather
import com.synexo.weatherapp.core.model.Resource
import com.synexo.weatherapp.search.domain.model.SearchResult
import com.synexo.weatherapp.search.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCurrentLocationWeatherUseCase @Inject constructor(
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
    private val geocodeRepository: GeocodeRepository,
    private val settingsRepository: SettingsRepository
) : BaseSuspendUseCase<Unit, Flow<Resource<GetCurrentLocationWeatherUseCase.Result>>> {

    override suspend fun invoke(params: Unit): Flow<Resource<Result>> {
        return safeTryCatchFlow {
            val language = Locale.getDefault().language

            val location = locationRepository
                .getCurrentLocation()
                .first()

            val weatherUnits = settingsRepository
                .getSettings()
                .first()
                .mapToWeatherUnits()

            val geocodeModel = geocodeRepository.getGeocodeDataFromLatLng(
                lat = location.lat,
                lng = location.lng,
                lang = language
            )

            val weatherData = weatherRepository.getWeatherData(
                lat = location.lat,
                lng = location.lng,
                weatherUnits = weatherUnits,
                lang = language
            )

            val placeId = geocodeModel.placeId

            // -1 if there is no place id in database
            val order = cityRepository.getCityDataOrder(placeId)

            val result = CityWeather(
                cityDetails = geocodeModel.mapToCityDetails(language),
                weather = weatherData,
                lat = location.lat,
                lng = location.lng,
                order = order,
                id = placeId
            )

            val searchResult = if (order == -1) {
                SearchResult.NEW
            } else {
                cityRepository.updateCityData(result)
                SearchResult.EXISTS
            }

            Result(result, searchResult)
        }
    }

    data class Result(
        val cityWeather: CityWeather,
        val searchResult: SearchResult
    )
}