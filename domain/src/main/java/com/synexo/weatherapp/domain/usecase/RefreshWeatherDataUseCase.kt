package com.synexo.weatherapp.domain.usecase

import com.synexo.weatherapp.core.BaseSuspendUseCase
import com.synexo.weatherapp.core.Resource
import com.synexo.weatherapp.domain.model.CityWeather
import com.synexo.weatherapp.domain.model.Units
import com.synexo.weatherapp.domain.repository.CityRepository
import com.synexo.weatherapp.domain.repository.WeatherRepository
import com.synexo.weatherapp.domain.service.SettingsService
import com.synexo.weatherapp.domain.util.safeTryCatchFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshWeatherDataUseCase @Inject constructor(
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository,
    private val settingsService: SettingsService
) : BaseSuspendUseCase<RefreshWeatherDataUseCase.Param, Flow<Resource<RefreshWeatherDataUseCase.Result>>> {

    override suspend fun invoke(params: Param): Flow<Resource<Result>> {
        return safeTryCatchFlow {
            val weatherUnits = settingsService
                .getSettings()
                .first()
                .mapToWeatherUnits()

            val weather = weatherRepository.getWeatherData(
                lat = params.cityWeather.lat,
                lng = params.cityWeather.lng,
                units = Units.Metric,
                weatherUnits = weatherUnits,
                lang = Locale.getDefault().language
            )

            val refreshedCityWeather = params.cityWeather.copy(weather = weather)

            cityRepository.updateCityData(refreshedCityWeather)

            Result(refreshedCityWeather)
        }
    }

    data class Param(
        val cityWeather: CityWeather
    )

    data class Result(
        val cityWeather: CityWeather
    )
}