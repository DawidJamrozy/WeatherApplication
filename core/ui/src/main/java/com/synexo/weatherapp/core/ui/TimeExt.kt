package com.synexo.weatherapp.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.synexo.weatherapp.core.model.weather.Time
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

const val DAY_DATE_MONTH_HOUR_MINUTE_PATTERN = "EEEE d MMMM, HH:mm"
const val DAY_DATE_HOUR_MINUTE_PATTERN = "EEEE HH:mm"
const val HOUR_MINUTE_PATTERN = "HH:mm"
const val DAY_DATE_MONTH_PATTERN = "EEEE d MMMM"
const val SHORT_DAY_DATE_MONTH_PATTERN = "EE d MMMM"

fun calculateDaylight(
    sunrise: Time,
    sunset: Time
): String {
    val zoneId = ZoneId.of(sunset.timezone)
    val sunriseTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(sunrise.timestamp), zoneId)
    val sunsetTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(sunset.timestamp), zoneId)
    val daylightDuration = Duration.between(sunriseTime, sunsetTime)
    val hours = daylightDuration.toHours()
    val minutes = daylightDuration.toMinutes() % 60
    return "$hours h $minutes min"
}

@Composable
fun Time.toReadableDate(
    pattern: String
): String {
    val zoneId = ZoneId.of(timezone)
    val now = LocalDate.now(zoneId)
    val localDate = getLocalDate()
    return when {
        localDate.isEqual(now) ->
            stringResource(R.string.common_today)

        localDate.isEqual(now.plusDays(1)) ->
            stringResource(R.string.common_tomorrow)

        else -> localDate.format(DateTimeFormatter.ofPattern(pattern))
            .replaceFirstChar { it.uppercase() }
    }
}

fun Time.getLocalDate(): LocalDate {
    val zoneId = ZoneId.of(timezone)
    val date = Date(timestamp * 1000L)
    return date.toInstant().atZone(zoneId).toLocalDate()
}

fun Time.toDate(pattern: String): String {
    val instant = Instant.ofEpochSecond(timestamp)
    val zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of(timezone))
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return formatter.format(zonedDateTime)
}

fun Time.isNow(): Boolean {
    val zoneId = ZoneId.of(timezone)
    val inputTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), zoneId)
    val now = LocalDateTime.now(zoneId)
    // Check if year, month, day, and hour are the same
    return inputTime.year == now.year &&
            inputTime.month == now.month &&
            inputTime.dayOfMonth == now.dayOfMonth &&
            inputTime.hour == now.hour
}


fun convertWindDegreesToDirection(degrees: Int): String {
    val directions = arrayOf("N", "NE", "E", "SE", "S", "SW", "W", "NW", "N")
    return directions[((degrees % 360) / 45)]
}

fun Time.getDateIfMidnight(): String? {
    val zoneId = ZoneId.of(timezone)
    val dateTime = Instant.ofEpochSecond(timestamp).atZone(zoneId).toLocalDateTime()

    return if (dateTime.hour == 0 && dateTime.minute == 0) {
        dateTime.format(DateTimeFormatter.ofPattern("d MMM"))
    } else {
        null
    }
}