package com.synexo.weatherapp.domain.usecase

import com.synexo.weatherapp.core.BaseSuspendUseCase
import com.synexo.weatherapp.core.Resource
import com.synexo.weatherapp.domain.model.CityWeather
import com.synexo.weatherapp.domain.model.Units
import com.synexo.weatherapp.domain.repository.GeocodeRepository
import com.synexo.weatherapp.domain.repository.WeatherRepository
import com.synexo.weatherapp.domain.service.SettingsService
import com.synexo.weatherapp.domain.util.safeTryCatchFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLocationWeatherFromAddressUseCase @Inject constructor(
    private val geocodeRepository: GeocodeRepository,
    private val weatherRepository: WeatherRepository,
    private val settingsService: SettingsService,
) : BaseSuspendUseCase<GetLocationWeatherFromAddressUseCase.Param, Flow<Resource<GetLocationWeatherFromAddressUseCase.Result>>> {

    override suspend fun invoke(params: Param): Flow<Resource<Result>> {
        return safeTryCatchFlow {
            val language = Locale.getDefault().language

            val weatherUnits = settingsService
                .getSettings()
                .first()
                .mapToWeatherUnits()

            val geocodeModel = geocodeRepository.getGeocodeData(
                name = params.address,
                lang = language
            )

            val weatherModel = weatherRepository.getWeatherData(
                lat = geocodeModel.location.lat,
                lng = geocodeModel.location.lng,
                units = Units.Metric,
                weatherUnits = weatherUnits,
                lang = language
            )

            val result = CityWeather(
                cityDetails = geocodeModel.mapToCityDetails(language),
                lat = geocodeModel.location.lat,
                lng = geocodeModel.location.lng,
                weather = weatherModel,
                order = 0,
                id = geocodeModel.placeId
            )

            Result(result)
        }
    }

    data class Param(
        val address: String
    )

    data class Result(
        val weather: CityWeather
    )
}