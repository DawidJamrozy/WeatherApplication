package com.synexo.weatherapp.util

import com.synexo.weatherapp.domain.model.CityWeather
import com.synexo.weatherapp.domain.model.Units
import com.synexo.weatherapp.domain.model.WeatherUnits
import com.synexo.weatherapp.domain.model.weather.CityDetails
import com.synexo.weatherapp.domain.model.weather.CurrentWeather
import com.synexo.weatherapp.domain.model.weather.DailyWeather
import com.synexo.weatherapp.domain.model.weather.Details
import com.synexo.weatherapp.domain.model.weather.FeelsLike
import com.synexo.weatherapp.domain.model.weather.HourlyWeather
import com.synexo.weatherapp.domain.model.weather.Percent
import com.synexo.weatherapp.domain.model.weather.Pressure
import com.synexo.weatherapp.domain.model.weather.Rain
import com.synexo.weatherapp.domain.model.weather.Snow
import com.synexo.weatherapp.domain.model.weather.Temp
import com.synexo.weatherapp.domain.model.weather.Temperature
import com.synexo.weatherapp.domain.model.weather.UVIndex
import com.synexo.weatherapp.domain.model.weather.Visibility
import com.synexo.weatherapp.domain.model.weather.Weather
import com.synexo.weatherapp.domain.model.weather.WindSpeed
import java.util.UUID
import kotlin.random.Random

object MockDataHelper {

    private val units = Units.Metric
    private val weatherUnits = WeatherUnits()

    fun createCityWeather(): CityWeather {
        return CityWeather(
            CityDetails(
                name = "Rzeszów",
                country = "Polska",
                administrativeArea = "Podkarpackie",
                language = "pl"
            ),
            lat = 50.04132,
            lng = 21.99901,
            order = 0,
            weather = Weather(
                currentWeather = createMockCurrent(),
                dailyWeather = (0 until 7).map { createDailyWeather() },
                hourlyWeather = (0 until 24).map { createHourlyWeather() },
                timezone = "Europe/London",
                timezoneOffset = 0,
                weatherUnits = weatherUnits,
                units = units,
            ),
            id = UUID.randomUUID().toString()
        )
    }

    fun createDailyWeather(): DailyWeather {
        return DailyWeather(
            clouds = Random.nextInt(0, 100),
            dewPoint = Temperature(Random.nextDouble(-10.0, 30.0), weatherUnits.degrees, units),
            timestamp = Random.nextLong(0, 1714069666),
            feelsLike = FeelsLike(
                day = Temperature(Random.nextDouble(-10.0, 30.0), weatherUnits.degrees, units),
                eve = Temperature(Random.nextDouble(-10.0, 30.0), weatherUnits.degrees, units),
                morn = Temperature(Random.nextDouble(-10.0, 30.0), weatherUnits.degrees, units),
                night = Temperature(Random.nextDouble(-10.0, 30.0), weatherUnits.degrees, units),
            ),
            humidity = Random.nextInt(0, 100),
            moonPhase = 0.0,
            moonrise = 0,
            moonset = 100,
            pop = 0.0,
            pressure = Pressure(Random.nextInt(0, 100), weatherUnits.pressure, units),
            sunrise = 0,
            sunset = 100,
            temp = Temp(
                day = Temperature(Random.nextDouble(-10.0, 30.0), weatherUnits.degrees, units),
                eve = Temperature(Random.nextDouble(-10.0, 30.0), weatherUnits.degrees, units),
                max = Temperature(Random.nextDouble(-10.0, 30.0), weatherUnits.degrees, units),
                min = Temperature(Random.nextDouble(-10.0, 30.0), weatherUnits.degrees, units),
                morn = Temperature(Random.nextDouble(-10.0, 30.0), weatherUnits.degrees, units),
                night = Temperature(Random.nextDouble(-10.0, 30.0), weatherUnits.degrees, units),
            ),
            uvi = UVIndex(Random.nextDouble(0.0, 10.0)),
            details = Details(
                description = "Clouds",
                icon = "04d",
                id = 804,
                main = "Clouds"
            ),
            windDeg = Random.nextInt(0, 360),
            windSpeed = WindSpeed(Random.nextDouble(0.0, 100.0), weatherUnits.wind, units),
            rain = 0.0,
            snow = 0.0,
            summary = "Clouds",
            windGust = WindSpeed(Random.nextDouble(0.0, 100.0), weatherUnits.wind, units),
            visibility = Visibility(Random.nextInt(0, 100)),
            timezone = "Europe/London"
        )
    }

    fun createHourlyWeather(): HourlyWeather {
        return HourlyWeather(
            clouds = Random.nextInt(0, 100),
            dewPoint = Temperature(Random.nextDouble(-10.0, 30.0), weatherUnits.degrees, units),
            timestamp = Random.nextLong(0, 1714069666),
            feelsLike = Temperature(Random.nextDouble(-10.0, 30.0), weatherUnits.degrees, units),
            humidity = Random.nextInt(0, 100),
            pop = 0.0,
            pressure = Pressure(Random.nextInt(0, 100), weatherUnits.pressure, units),
            temp = Temperature(Random.nextDouble(-10.0, 30.0), weatherUnits.degrees, units),
            uvi = UVIndex(Random.nextDouble(0.0, 10.0)),
            visibility = Visibility(Random.nextInt(0, 100)),
            details = Details(
                description = "Clouds",
                icon = "04d",
                id = 804,
                main = "Clouds"
            ),
            windDeg = Random.nextInt(0, 360),
            windSpeed = WindSpeed(Random.nextDouble(0.0, 100.0), weatherUnits.wind, units),
            timezone = "Europe/London",
            snow = Snow(0.0),
            rain = Rain(0.0),
            windGust = WindSpeed(Random.nextDouble(0.0, 100.0), weatherUnits.wind, units)
        )
    }

    fun createMockCurrent(): CurrentWeather {
        return CurrentWeather(
            clouds = Random.nextInt(0, 100),
            dewPoint = Temperature(Random.nextDouble(-10.0, 30.0), weatherUnits.degrees, units),
            timestamp = Random.nextLong(0, 1714069666),
            feelsLike = Temperature(Random.nextDouble(-10.0, 30.0), weatherUnits.degrees, units),
            humidity = Percent(Random.nextInt(0, 100)),
            pressure = Pressure(Random.nextInt(0, 100), weatherUnits.pressure, units),
            sunrise = 0,
            sunset = 100,
            temp = Temperature(Random.nextDouble(-10.0, 30.0), weatherUnits.degrees, units),
            uvi = UVIndex(Random.nextDouble(0.0, 10.0)),
            visibility = Visibility(Random.nextInt(0, 100)),
            details = Details(
                description = "Clouds",
                icon = "04d",
                id = 804,
                main = "Clouds"
            ),
            windDeg = Random.nextInt(0, 360),
            windSpeed = WindSpeed(Random.nextDouble(0.0, 100.0), weatherUnits.wind, units),
            timezone = "Europe/London",
            snow = Snow(0.0),
            rain = Rain(0.0),
            windGust = WindSpeed(Random.nextDouble(0.0, 100.0), weatherUnits.wind, units)
        )
    }
}