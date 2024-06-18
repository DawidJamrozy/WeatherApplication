package com.synexo.weatherapp.search.data.di

import com.synexo.weatherapp.search.data.repository.LocationRepositoryImpl
import com.synexo.weatherapp.search.data.repository.SearchCityRepositoryImpl
import com.synexo.weatherapp.search.domain.repository.LocationRepository
import com.synexo.weatherapp.search.domain.repository.SearchRepository
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
    abstract fun bindLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository

    @Binds
    @Singleton
    abstract fun bindSearchCityRepository(
        searchCityRepositoryImpl: SearchCityRepositoryImpl
    ): SearchRepository

}