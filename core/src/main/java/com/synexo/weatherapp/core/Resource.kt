package com.synexo.weatherapp.core

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val error: AppError) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}

sealed class AppError(message: String? = null) : Exception() {
    data object NoInternetConnection : AppError()
    data object NotFoundError : AppError()
    data object UnauthorizedError : AppError()
    data object ForbiddenError : AppError()
    data object TimeoutError : AppError()
    data object InternalServerError : AppError()
    data class GoogleApiException(val status: Int): AppError()
    data class UnknownError(override val message: String? = "Unknown error") : AppError(message)
}