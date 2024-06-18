package com.synexo.weatherapp.core.data.util

import com.synexo.weatherapp.core.database.entity.CityEntity
import com.synexo.weatherapp.core.model.CityWeather
import com.synexo.weatherapp.core.model.weather.CurrentWeather
import com.synexo.weatherapp.core.model.weather.DailyWeather
import com.synexo.weatherapp.core.model.weather.Details
import com.synexo.weatherapp.core.model.weather.FeelsLike
import com.synexo.weatherapp.core.model.weather.HourlyWeather
import com.synexo.weatherapp.core.model.weather.Temp
import com.synexo.weatherapp.core.model.weather.Weather
import com.synexo.weatherapp.core.network.model.weather.CurrentWeatherData
import com.synexo.weatherapp.core.network.model.weather.DailyWeatherData
import com.synexo.weatherapp.core.network.model.weather.DetailsData
import com.synexo.weatherapp.core.network.model.weather.FeelsLikeWeatherData
import com.synexo.weatherapp.core.network.model.weather.HourlyWeatherData
import com.synexo.weatherapp.core.network.model.weather.Rain
import com.synexo.weatherapp.core.network.model.weather.Snow
import com.synexo.weatherapp.core.network.model.weather.TempWeatherData
import com.synexo.weatherapp.core.network.model.weather.WeatherResponse

internal fun CityWeather.mapToEntity(order: Int?, weatherResponse: String): CityEntity =
    CityEntity(
        weather = weatherResponse,
        cityName = this.cityDetails.name,
        countryName = this.cityDetails.country,
        adminArea = this.cityDetails.administrativeArea,
        language = this.cityDetails.language,
        lat = this.lat,
        lon = this.lng,
        order = order ?: this.order,
        id = this.id,
        updatedAt = System.currentTimeMillis()
    )

internal fun Weather.toWeatherResponse(): WeatherResponse {
    return WeatherResponse(
        currentWeatherData = this.currentWeather.toCurrent(),
        dailyWeatherData = this.dailyWeather.map { it.toDaily() },
        hourlyWeatherData = this.hourlyWeather.map { it.toHourly() },
        timezone = this.timezone,
        timezoneOffset = this.timezoneOffset
    )
}

internal fun CurrentWeather.toCurrent(): CurrentWeatherData {
    return CurrentWeatherData(
        clouds = this.clouds,
        dewPoint = this.dewPoint.rawValue,
        timestamp = this.timestamp,
        feelsLike = this.feelsLike.rawValue,
        humidity = this.humidity.value,
        pressure = this.pressure.rawValue,
        sunrise = this.sunrise,
        sunset = this.sunset,
        temp = this.temp.rawValue,
        uvi = this.uvi.value,
        visibility = this.visibility?.value,
        details = this.details.toWeather().let { listOf(it) },
        windDeg = this.windDeg,
        windSpeed = this.windSpeed.rawValue
    )
}

internal fun DailyWeather.toDaily(): DailyWeatherData {
    return DailyWeatherData(
        clouds = this.clouds,
        dewPoint = this.dewPoint.rawValue,
        timestamp = this.timestamp,
        feelsLikeWeatherData = this.feelsLike.toFeelsLike(),
        humidity = this.humidity,
        moonPhase = this.moonPhase,
        moonrise = this.moonrise,
        moonset = this.moonset,
        pop = this.pop,
        pressure = this.pressure.rawValue,
        sunrise = this.sunrise,
        sunset = this.sunset,
        tempWeatherData = this.temp.toTemp(),
        uvi = this.uvi.value,
        details = this.details.toWeather().let { listOf(it) },
        windDeg = this.windDeg,
        windSpeed = this.windSpeed.rawValue,
        rain = this.rain,
        snow = this.snow,
        summary = this.summary,
        windGust = this.windGust?.rawValue,
        visibility = this.visibility?.value
    )
}

internal fun HourlyWeather.toHourly(): HourlyWeatherData {
    return HourlyWeatherData(
        clouds = this.clouds,
        dewPoint = this.dewPoint.rawValue,
        timestamp = this.timestamp,
        feelsLike = this.feelsLike.rawValue,
        humidity = this.humidity,
        pop = this.pop,
        pressure = this.pressure.rawValue,
        temp = this.temp.rawValue,
        uvi = this.uvi.value,
        visibility = this.visibility?.value,
        details = this.details.toWeather().let { listOf(it) },
        windDeg = this.windDeg,
        windSpeed = this.windSpeed.rawValue,
        rain = this.rain?.let { Rain(it.h) },
        snow = this.snow?.let { Snow(it.h) },
    )
}

internal fun FeelsLike.toFeelsLike(): FeelsLikeWeatherData {
    return FeelsLikeWeatherData(
        day = this.day.rawValue,
        eve = this.eve.rawValue,
        morn = this.morn.rawValue,
        night = this.night.rawValue
    )
}

internal fun Details.toWeather(): DetailsData {
    return DetailsData(
        description = this.description,
        icon = this.icon,
        id = this.id,
        main = this.main
    )
}

internal fun Temp.toTemp(): TempWeatherData {
    return TempWeatherData(
        day = this.day.rawValue,
        eve = this.eve.rawValue,
        max = this.max.rawValue,
        min = this.min.rawValue,
        morn = this.morn.rawValue,
        night = this.night.rawValue
    )
}