package com.synexo.weatherapp.core.network.util

import com.synexo.weatherapp.core.model.AppError
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> safeApiCall(call: suspend () -> T): T {
    try {
        return call()
    } catch (e: IOException) {
        throw AppError.NoInternetConnection
    } catch (e: SocketTimeoutException) {
        throw AppError.TimeoutError
    } catch (e: HttpException) {
        when (e.code()) {
            401 -> throw AppError.UnauthorizedError
            403 -> throw AppError.ForbiddenError
            404 -> throw AppError.NotFoundError
            500 -> throw AppError.InternalServerError
            else -> throw AppError.UnknownError(e.message())
        }
    }
}