package com.synexo.weatherapp.core.domain.util

import com.synexo.weatherapp.core.model.AppError
import com.synexo.weatherapp.core.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

suspend fun <T> safeTryCatchFlow(call: suspend () -> T): Flow<Resource<T>> {
    return flow {
        emit(Resource.Loading)
        val result = call()
        emit(Resource.Success(result))
    }.catch { error ->
        when (error) {
            is AppError -> emit(Resource.Error(error))
            else -> emit(Resource.Error(AppError.UnknownError(error.message)))
        }
    }
}