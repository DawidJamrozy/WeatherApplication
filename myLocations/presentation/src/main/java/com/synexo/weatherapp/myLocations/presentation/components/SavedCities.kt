package com.synexo.weatherapp.myLocations.presentation.components


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.synexo.weatherapp.core.model.CityWeather
import com.synexo.weatherapp.core.model.MockDataHelper
import com.synexo.weatherapp.core.ui.HOUR_MINUTE_PATTERN
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.WeatherAppTheme
import com.synexo.weatherapp.core.ui.components.LazyDraggableColumn
import com.synexo.weatherapp.core.ui.toDate
import com.synexo.weatherapp.core.ui.util.getIconResId
import com.synexo.weatherapp.myLocations.presentation.MyLocationsScreenViewEvent

@Composable
fun SavedCities(
    cities: List<CityWeather>,
    onEvent: (MyLocationsScreenViewEvent) -> Unit,
    isInEditMode: Boolean,
    onMove: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val maxWidth = LocalConfiguration.current.screenWidthDp.dp
    val iconWidthWithPadding = 20.dp + 50.dp
    val animatedWidth by animateDpAsState(
        targetValue = if (isInEditMode) maxWidth - iconWidthWithPadding else maxWidth,
        label = "",
        animationSpec = tween(500)
    )

    LazyDraggableColumn(
        modifier = modifier,
        items = cities,
        onMove = onMove,
        isDragEnabled = isInEditMode
    ) { city, isDragging, index ->
        val elevation by animateDpAsState(if (isDragging) 8.dp else 2.dp, label = "elevation")
        Row {
            WeatherCard(
                modifier = Modifier
                    .width(animatedWidth),
                cityDetails = city.cityDetails,
                icon = city.weather.currentWeather.details.getIconResId(),
                time = city.weather.currentWeather.getTimestamp().toDate(
                    pattern = HOUR_MINUTE_PATTERN
                ),
                description = city.weather.currentWeather.details.description.replaceFirstChar { it.uppercase() },
                temperature = city.weather.currentWeather.temp.getValueWithUnit(),
                highLowTemperature = "↑${city.weather.dailyWeather.first().temp.max.getValueWithUnit()} ↓${city.weather.dailyWeather.first().temp.min.getValueWithUnit()}",
                elevation = elevation,
                onClick = { onEvent(MyLocationsScreenViewEvent.GoToWeatherScreen(index)) }
            )

            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 10.dp),
                onClick = {
                    onEvent(
                        MyLocationsScreenViewEvent.ShowDeleteLocationDialog(city.cityDetails.name, index)
                    )
                }
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clip(CircleShape),
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null,
                    tint = LocalCustomColorsPalette.current.textClickable
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun SavedCitiesListPreview(
    @PreviewParameter(SavedCitiesListStateParameterProvider::class) state: Boolean
) {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            SavedCities(
                cities = listOf(MockDataHelper.createCityWeather()),
                onEvent = {},
                isInEditMode = state,
                onMove = { _, _ -> }
            )
        }
    }
}

private class SavedCitiesListStateParameterProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}
