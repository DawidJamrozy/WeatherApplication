package com.synexo.weatherapp.data.di

import com.synexo.weatherapp.data.repository.CityRepositoryImpl
import com.synexo.weatherapp.data.repository.DataStoreRepositoryImpl
import com.synexo.weatherapp.data.repository.GeocodeRepositoryImpl
import com.synexo.weatherapp.data.repository.LocationRepositoryImpl
import com.synexo.weatherapp.data.repository.SearchCityRepositoryImpl
import com.synexo.weatherapp.data.repository.WeatherRepositoryImpl
import com.synexo.weatherapp.domain.repository.CityRepository
import com.synexo.weatherapp.domain.repository.DataStoreRepository
import com.synexo.weatherapp.domain.repository.GeocodeRepository
import com.synexo.weatherapp.domain.repository.LocationRepository
import com.synexo.weatherapp.domain.repository.SearchRepository
import com.synexo.weatherapp.domain.repository.WeatherRepository
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
    abstract fun bindGeocodeRepository(
        geocodeRepositoryImpl: GeocodeRepositoryImpl
    ): GeocodeRepository

    @Binds
    @Singleton
    abstract fun bindCityRepository(
        cityRepositoryImpl: CityRepositoryImpl
    ): CityRepository

    @Binds
    @Singleton
    abstract fun bindDataStoreRepository(
        dataStoreRepositoryImpl: DataStoreRepositoryImpl
    ): DataStoreRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository

    @Binds
    @Singleton
    abstract fun bindSearchCityRepository(
        searchCityRepositoryImpl: SearchCityRepositoryImpl
    ): SearchRepository

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

}