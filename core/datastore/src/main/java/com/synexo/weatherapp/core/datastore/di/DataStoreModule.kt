package com.synexo.weatherapp.core.datastore.di

import com.synexo.weatherapp.core.datastore.DataStoreProvider
import com.synexo.weatherapp.core.datastore.DataStoreProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataStoreModule {

    @Binds
    @Singleton
    abstract fun bindDataStoreProvider(
        dataStoreProviderImpl: DataStoreProviderImpl
    ): DataStoreProvider

}