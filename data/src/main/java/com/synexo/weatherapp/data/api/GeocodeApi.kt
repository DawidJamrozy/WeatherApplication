package com.synexo.weatherapp.data.api

import com.synexo.weatherapp.data.model.geocode.GeocodeResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface GeocodeApi {

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