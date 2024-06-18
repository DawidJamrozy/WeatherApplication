package com.synexo.weatherapp.core.network.api

import com.synexo.weatherapp.core.network.model.geocode.GeocodeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodeApi {

    @GET("json?")
    suspend fun getGeocodeData(
        @Query("latlng") latlng: String,
        @Query("location_type") locationType: String,
        @Query("result_type") resultType: String,
        @Query("language") lang: String,
        @Query("key") key: String
    ): GeocodeResponse

    @GET("json?")
    suspend fun getGeocodeData(
        @Query("address") address: String,
        @Query("language") lang: String,
        @Query("key") key: String,
    ): GeocodeResponse

}