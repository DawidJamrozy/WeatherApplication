package com.synexo.weatherapp.myLocations.domain.usecase

import com.synexo.weatherapp.core.domain.usecase.base.BaseSuspendUseCase
import com.synexo.weatherapp.myLocations.domain.repository.CityDataRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DeleteCityUseCase @Inject constructor(
    private val cityDataRepository: CityDataRepository
) : BaseSuspendUseCase<DeleteCityUseCase.Params, Unit> {

    override suspend fun invoke(params: Params) {
        cityDataRepository.deleteCityData(params.id)
    }

    data class Params(
        val id: String
    )

}