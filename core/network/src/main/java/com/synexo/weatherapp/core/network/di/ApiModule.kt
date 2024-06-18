package com.synexo.weatherapp.core.network.di

import com.synexo.weatherapp.core.network.api.GeocodeApi
import com.synexo.weatherapp.core.network.api.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ApiModule {

    @Provides
    @Singleton
    fun provideGeocodeApi(
        @Named("Geocode") retrofit: Retrofit
    ): GeocodeApi =
        retrofit.create(GeocodeApi::class.java)

    @Provides
    @Singleton
    fun provideWeatherApi(
        @Named("Weather") retrofit: Retrofit
    ): WeatherApi =
        retrofit.create(WeatherApi::class.java)

}