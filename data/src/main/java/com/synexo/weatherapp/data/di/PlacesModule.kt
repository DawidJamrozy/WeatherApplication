package com.synexo.weatherapp.data.di

import android.content.Context
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.synexo.weatherapp.data.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class PlacesModule {

    @Provides
    fun providePlacesClient(
        @ApplicationContext context: Context
    ): PlacesClient {
        if(!Places.isInitialized()) {
            Places.initialize(context, BuildConfig.GOOGLE_API_KEY)
        }
        return Places.createClient(context)
    }

}