package com.synexo.weatherapp.weather.domain.usecase

import com.synexo.weatherapp.core.domain.repository.CityRepository
import com.synexo.weatherapp.core.domain.repository.SettingsRepository
import com.synexo.weatherapp.core.domain.repository.WeatherRepository
import com.synexo.weatherapp.core.domain.usecase.base.BaseSuspendUseCase
import com.synexo.weatherapp.core.domain.util.safeTryCatchFlow
import com.synexo.weatherapp.core.model.CityWeather
import com.synexo.weatherapp.core.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshWeatherDataUseCase @Inject constructor(
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository,
    private val settingsRepository: SettingsRepository
) : BaseSuspendUseCase<RefreshWeatherDataUseCase.Param, Flow<Resource<RefreshWeatherDataUseCase.Result>>> {

    override suspend fun invoke(params: Param): Flow<Resource<Result>> {
        return safeTryCatchFlow {
            val weatherUnits = settingsRepository
                .getSettings()
                .first()
                .mapToWeatherUnits()

            val weather = weatherRepository.getWeatherData(
                lat = params.cityWeather.lat,
                lng = params.cityWeather.lng,
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