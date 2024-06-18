package com.synexo.weatherapp.myLocations.data.di

import com.synexo.weatherapp.myLocations.data.repository.CityDataRepositoryImpl
import com.synexo.weatherapp.myLocations.domain.repository.CityDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MyLocationsRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCityDataRepository(
        cityDataRepositoryImpl: CityDataRepositoryImpl
    ): CityDataRepository

}