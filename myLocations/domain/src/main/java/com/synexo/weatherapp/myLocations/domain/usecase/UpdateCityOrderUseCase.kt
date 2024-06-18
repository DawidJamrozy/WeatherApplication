package com.synexo.weatherapp.myLocations.domain.usecase

import com.synexo.weatherapp.core.domain.usecase.base.BaseSuspendUseCase
import com.synexo.weatherapp.myLocations.domain.repository.CityDataRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateCityOrderUseCase @Inject constructor(
    private val cityDataRepository: CityDataRepository
): BaseSuspendUseCase<UpdateCityOrderUseCase.Params, Unit> {

    override suspend fun invoke(params: Params) {
        cityDataRepository.updateCityDataOrder(params.idToPositionMap)
    }

    data class Params(
        val idToPositionMap: Map<String, Int>
    )

}