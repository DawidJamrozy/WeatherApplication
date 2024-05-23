package com.synexo.weatherapp.domain.usecase

import com.synexo.weatherapp.core.BaseSuspendUseCase
import com.synexo.weatherapp.domain.repository.CityRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DeleteLocationUseCase @Inject constructor(
    private val cityRepository: CityRepository
) : BaseSuspendUseCase<DeleteLocationUseCase.Params, Unit> {

    override suspend fun invoke(params: Params) {
        cityRepository.deleteCityData(params.id)
    }

    data class Params(
        val id: String
    )

}