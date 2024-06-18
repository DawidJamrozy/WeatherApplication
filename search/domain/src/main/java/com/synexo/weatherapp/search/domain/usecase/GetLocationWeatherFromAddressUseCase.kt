package com.synexo.weatherapp.search.domain.usecase

import com.synexo.weatherapp.core.domain.repository.SettingsRepository
import com.synexo.weatherapp.core.domain.repository.WeatherRepository
import com.synexo.weatherapp.core.domain.usecase.base.BaseSuspendUseCase
import com.synexo.weatherapp.core.domain.util.safeTryCatchFlow
import com.synexo.weatherapp.core.model.CityWeather
import com.synexo.weatherapp.core.model.Resource
import com.synexo.weatherapp.core.domain.repository.GeocodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLocationWeatherFromAddressUseCase @Inject constructor(
    private val geocodeRepository: GeocodeRepository,
    private val weatherRepository: WeatherRepository,
    private val settingsRepository: SettingsRepository
) : BaseSuspendUseCase<GetLocationWeatherFromAddressUseCase.Param, Flow<Resource<GetLocationWeatherFromAddressUseCase.Result>>> {

    override suspend fun invoke(params: Param): Flow<Resource<Result>> {
        return safeTryCatchFlow {
            val language = Locale.getDefault().language

            val weatherUnits = settingsRepository
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