package com.synexo.weatherapp.settings.data.di

import com.synexo.weatherapp.core.domain.repository.SettingsRepository
import com.synexo.weatherapp.settings.data.repository.SettingsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SettingsRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): SettingsRepository

}