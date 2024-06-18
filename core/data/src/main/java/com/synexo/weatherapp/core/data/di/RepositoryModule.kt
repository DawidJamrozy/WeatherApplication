package com.synexo.weatherapp.core.data.di

import com.synexo.weatherapp.core.data.repository.CityRepositoryImpl
import com.synexo.weatherapp.core.data.repository.GeocodeRepositoryImpl
import com.synexo.weatherapp.core.data.repository.WeatherRepositoryImpl
import com.synexo.weatherapp.core.domain.repository.CityRepository
import com.synexo.weatherapp.core.domain.repository.GeocodeRepository
import com.synexo.weatherapp.core.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCityRepository(
        cityRepositoryImpl: CityRepositoryImpl
    ): CityRepository

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindGeocodeRepository(
        geocodeRepositoryImpl: GeocodeRepositoryImpl
    ): GeocodeRepository

}