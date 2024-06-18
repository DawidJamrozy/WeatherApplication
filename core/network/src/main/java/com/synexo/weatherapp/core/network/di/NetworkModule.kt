package com.synexo.weatherapp.core.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.synexo.weatherapp.core.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {

    private companion object {
        /** Connection timeout in seconds */
        const val CONNECT_TIMEOUT = 30L

        /** Read timeout in seconds */
        const val READ_TIMEOUT = 30L

        /** Write timeout in seconds */
        const val WRITE_TIMEOUT = 30L

        val contentType = "application/json".toMediaType()
    }

    @Provides
    @Singleton
    fun provideNetworkJsonSerializer(): Json =
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }

    /**
     * Provides [OkHttpClient] instance to dependency graph
     * @param loggingInterceptor [HttpLoggingInterceptor] instance
     * @return [OkHttpClient] instance
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .apply {
                connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                addInterceptor(loggingInterceptor)
            }
            .build()

    /**
     * Provides [HttpLoggingInterceptor.Logger] instance to dependency graph
     * @return [HttpLoggingInterceptor.Logger] instance
     */
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptorLogger(): HttpLoggingInterceptor.Logger =
        HttpLoggingInterceptor.Logger { println(it) }

    /**
     * Provides [HttpLoggingInterceptor] instance to dependency graph
     * @param httpLoggingInterceptorLogger [HttpLoggingInterceptor.Logger] instance
     * @return [HttpLoggingInterceptor] instance
     */
    @Provides
    @Singleton
    fun provideLoggingInterceptor(httpLoggingInterceptorLogger: HttpLoggingInterceptor.Logger): HttpLoggingInterceptor =
        HttpLoggingInterceptor(httpLoggingInterceptorLogger)
            .apply { level = HttpLoggingInterceptor.Level.BODY }

    /**
     * Provides [Retrofit] instance to dependency graph. This instance is used to create API instances
     * Adds [Json] converter factory to [Retrofit] instance
     * @param client [OkHttpClient] instance
     * @return [Retrofit] instance
     */
    @Provides
    @Singleton
    @Named("Geocode")
    fun provideGeocodeRetrofit(
        client: OkHttpClient,
        json: Json
    ): Retrofit =
        Retrofit
            .Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(BuildConfig.GOOGLE_API_URL)
            .client(client)
            .build()

    @Provides
    @Singleton
    @Named("Weather")
    fun provideWeatherRetrofit(
        client: OkHttpClient,
        json: Json
    ): Retrofit =
        Retrofit
            .Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(BuildConfig.WEATHER_API_URL)
            .client(client)
            .build()
}