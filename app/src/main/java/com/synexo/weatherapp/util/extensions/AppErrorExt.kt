package com.synexo.weatherapp.util.extensions

import com.synexo.weatherapp.R
import com.synexo.weatherapp.core.AppError

fun AppError.getErrorMessage(): Int {
    return when (this) {
        AppError.NoInternetConnection -> R.string.error_no_internet_connection
        AppError.NotFoundError -> R.string.error_not_found
        AppError.UnauthorizedError -> R.string.error_unauthorized
        AppError.ForbiddenError -> R.string.error_forbidden
        AppError.TimeoutError -> R.string.error_timeout
        AppError.InternalServerError -> R.string.error_internal_server
        is AppError.GoogleApiException -> GoogleApiErrorUtils.getErrorStringRes(status)
        is AppError.UnknownError -> R.string.error_unknown
    }
}

private object GoogleApiErrorUtils {
    const val SIGN_IN_REQUIRED: Int = 4
    const val INVALID_ACCOUNT: Int = 5
    const val NETWORK_ERROR: Int = 7
    const val INTERNAL_ERROR: Int = 8
    const val DEVELOPER_ERROR: Int = 10
    const val ERROR: Int = 13
    const val INTERRUPTED: Int = 14
    const val TIMEOUT: Int = 15
    const val CANCELED: Int = 16
    const val API_NOT_CONNECTED: Int = 17

    fun getErrorStringRes(statusCode: Int): Int {
        return when (statusCode) {
            NETWORK_ERROR -> R.string.error_no_internet_connection
            API_NOT_CONNECTED -> R.string.error_api_not_connected
            TIMEOUT -> R.string.error_timeout
            DEVELOPER_ERROR -> R.string.error_developer_error
            INTERNAL_ERROR -> R.string.error_internal_error
            INVALID_ACCOUNT -> R.string.error_invalid_account
            SIGN_IN_REQUIRED -> R.string.error_sign_in_required
            CANCELED -> R.string.error_canceled
            ERROR -> R.string.error_generic
            INTERRUPTED -> R.string.error_interrupted
            else -> R.string.error_unknown
        }
    }
}