package com.synexo.weatherapp.domain.usecase

import com.synexo.weatherapp.core.BaseSuspendUseCase
import com.synexo.weatherapp.domain.model.CityShortData
import com.synexo.weatherapp.domain.model.Units
import com.synexo.weatherapp.domain.repository.CityRepository
import com.synexo.weatherapp.domain.repository.GeocodeRepository
import com.synexo.weatherapp.domain.repository.WeatherRepository
import com.synexo.weatherapp.domain.service.SettingsService
import kotlinx.coroutines.flow.first
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds

@Singleton
class RefreshAllWeatherDataUseCase @Inject constructor(
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository,
    private val geocodeRepository: GeocodeRepository,
    private val settingsService: SettingsService,
) : BaseSuspendUseCase<Unit, Unit> {

    override suspend fun invoke(params: Unit) {
        val language = Locale.getDefault().language

        val currentTimestamp =  System.currentTimeMillis().milliseconds

        val weatherUnits = settingsService
            .getSettings()
            .first()
            .mapToWeatherUnits()

        try {
            cityRepository
                .getAllCityShortData()
                .filter { it.shouldUpdate(language, currentTimestamp) }
                .map {
                    // If the language is different from the one stored in the database, update the city details to current language
                    val cityDetails = if (language != it.language) {
                        geocodeRepository
                            .getGeocodeDataFromLatLng(
                                lat = it.lat,
                                lng = it.lng,
                                lang = language
                            )
                            .mapToCityDetails(language)
                    } else {
                        null
                    }

                    val weather = weatherRepository.getWeatherData(
                        lat = it.lat,
                        lng = it.lng,
                        units = Units.Metric,
                        weatherUnits = weatherUnits,
                        lang = language
                    )

                    cityRepository
                        .updateCityData(
                            it.id,
                            weather,
                            cityDetails,
                        )
                }
        } catch (e: Exception) {
            e.printStackTrace() // Print to console and ignore exceptions
        }
    }

    private fun CityShortData.shouldUpdate(language: String, currentTimestamp: Duration): Boolean {
        val difference = currentTimestamp - updatedAt.milliseconds
        return difference > 1.hours || language != this.language
    }

}