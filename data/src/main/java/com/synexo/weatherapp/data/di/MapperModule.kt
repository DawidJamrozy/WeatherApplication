package com.synexo.weatherapp.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class MapperModule {

    @Provides
    @Singleton
    fun provideJsonSerializer(): Json =
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }

}