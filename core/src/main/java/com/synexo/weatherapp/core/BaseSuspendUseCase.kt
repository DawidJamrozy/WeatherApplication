package com.synexo.weatherapp.core

interface BaseSuspendUseCase<Params, Result> {

    suspend operator fun invoke(params: Params): Result

}

interface BaseUseCase<Params, Result> {

    operator fun invoke(params: Params): Result

}