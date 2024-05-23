package com.synexo.weatherapp.data.di

import android.content.Context
import androidx.room.Room
import com.synexo.weatherapp.data.database.WeatherDatabase
import com.synexo.weatherapp.data.database.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DatabaseModule {

    private companion object {
        const val DATABASE_NAME = "weather-database.db"
    }

    @Provides
    @Singleton
    fun provideMetricDatabase(
        @ApplicationContext context: Context,
    ): WeatherDatabase =
        Room
            .databaseBuilder(
                context,
                WeatherDatabase::class.java,
                DATABASE_NAME,
            )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideWeatherDao(
        database: WeatherDatabase,
    ): WeatherDao =
        database.getWeatherDao()

}