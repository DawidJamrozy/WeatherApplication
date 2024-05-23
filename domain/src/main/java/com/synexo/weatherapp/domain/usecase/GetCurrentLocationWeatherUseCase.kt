package com.synexo.weatherapp.domain.usecase

import com.synexo.weatherapp.core.BaseSuspendUseCase
import com.synexo.weatherapp.core.Resource
import com.synexo.weatherapp.domain.model.CityWeather
import com.synexo.weatherapp.domain.model.SearchResult
import com.synexo.weatherapp.domain.model.Units
import com.synexo.weatherapp.domain.repository.CityRepository
import com.synexo.weatherapp.domain.repository.GeocodeRepository
import com.synexo.weatherapp.domain.repository.LocationRepository
import com.synexo.weatherapp.domain.repository.WeatherRepository
import com.synexo.weatherapp.domain.service.SettingsService
import com.synexo.weatherapp.domain.util.safeTryCatchFlow
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
    private val settingsService: SettingsService
) : BaseSuspendUseCase<Unit, Flow<Resource<GetCurrentLocationWeatherUseCase.Result>>> {

    override suspend fun invoke(params: Unit): Flow<Resource<Result>> {
        return safeTryCatchFlow {
            val language = Locale.getDefault().language

            val location = locationRepository
                .getCurrentLocation()
                .first()

            val weatherUnits = settingsService
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
                units = Units.Metric,
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