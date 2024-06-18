package com.synexo.weatherapp.core.model

sealed class AppError(message: String? = null) : Exception(message) {
    data object NoInternetConnection : AppError()
    data object NotFoundError : AppError()
    data object UnauthorizedError : AppError()
    data object ForbiddenError : AppError()
    data object TimeoutError : AppError()
    data object InternalServerError : AppError()
    data class GoogleApiException(val status: Int): AppError()
    data class UnknownError(override val message: String? = "Unknown error") : AppError(message)
}