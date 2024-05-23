package com.synexo.weatherapp.data.api

import com.synexo.weatherapp.data.model.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface WeatherApi {

    @GET("onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") appId: String,
        @Query("lang") lang: String
    ): WeatherResponse

}