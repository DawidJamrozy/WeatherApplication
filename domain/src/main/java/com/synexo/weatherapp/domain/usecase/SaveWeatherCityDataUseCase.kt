package com.synexo.weatherapp.domain.usecase

import com.synexo.weatherapp.core.BaseSuspendUseCase
import com.synexo.weatherapp.domain.model.CityWeather
import com.synexo.weatherapp.domain.repository.CityRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveWeatherCityDataUseCase @Inject constructor(
    private val cityRepository: CityRepository
) : BaseSuspendUseCase<SaveWeatherCityDataUseCase.Params, Unit> {

    override suspend fun invoke(params: Params) {
        cityRepository.saveCityData(params.cityWeather)
    }

    data class Params(
        val cityWeather: CityWeather
    )

}